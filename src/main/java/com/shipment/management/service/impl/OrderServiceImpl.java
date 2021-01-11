package com.shipment.management.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipment.management.constants.Constants;
import com.shipment.management.domain.OrderEntity;
import com.shipment.management.enums.OrderStatusEnum;
import com.shipment.management.exceptions.OrderException;
import com.shipment.management.repository.OrderRepository;
import com.shipment.management.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * 创建货运订单
	 * 
	 * @param quantity
	 * @return
	 */
	@Transactional
	@Override
	public OrderEntity orderCreate(Long quantity) {
		// 记录货运主订单
		OrderEntity orderEntity = orderRepository
				.save(new OrderEntity(null, quantity, OrderStatusEnum.ORDER_CREATE.getCode(), Constants.ROOT_FLAG));
		orderEntity.setMasterId(orderEntity.getId());
		orderRepository.saveAndFlush(orderEntity);
		log.info("创建货运订单 orderEntity={}", orderEntity);
		return orderEntity;
	}

	/**
	 * 拆分货运订单
	 * 
	 * @param orderId
	 * @return
	 * @throws OrderException
	 */
	@Transactional
	@Override
	public List<OrderEntity> orderSplit(Long orderId, List<Long> quantityList) throws OrderException {
		// 校验订单是否存在
		Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
		if (!orderEntityOptional.isPresent()) {
			// 返回失败
			log.warn("拆分订单订单号不存在 orderId={}", orderId);
			throw new OrderException("拆分订单不存在");
		}
		OrderEntity orderEntity = orderEntityOptional.get();
		log.info("拆分前的货运订单:{}", orderEntity);
		// 校验订单状态是否正常
		this.checkOrderStatus(orderEntity.getStatus());
		// 校验订单金额和输入的拆分订单金额是否一致
		Long quantityTotal = 0L;
		for (Long quantity : quantityList) {
			quantityTotal += quantity;
		}
		if (quantityTotal != orderEntity.getQuantity()) {
			// 返回失败
			log.warn("拆分订单传入的金额不正确 拆分传入订单金额{} 订单金额={}", quantityTotal, orderEntity.getQuantity());
			throw new OrderException("拆分订单传入的金额不正确");
		}

		// 保存拆分的订单
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		for (Long quantity : quantityList) {
			orderEntityList.add(new OrderEntity(orderEntity.getMasterId(), quantity,
					OrderStatusEnum.ORDER_CREATE.getCode(), Constants.SUB_FLAG));
		}
		List<OrderEntity> orderEntityListNew = orderRepository.saveAll(orderEntityList);

		// 更新原订单为已拆分
		orderEntity.setStatus(OrderStatusEnum.ORDER_SPLIT.getCode());
		orderRepository.saveAndFlush(orderEntity);

		log.info("拆分后的货运订单:{}", orderEntityListNew);

		return orderEntityListNew;
	}

	/**
	 * 合并货运订单
	 * 
	 * @param orderIdList
	 * @return
	 * @throws OrderException
	 */
	@Transactional
	@Override
	public OrderEntity orderMerge(List<Long> orderIdList) throws OrderException {
		// 先查找所有要合并的货运订单
		Optional<List<OrderEntity>> orderEntityListOptional = orderRepository.findByIdIn(orderIdList);
		if (!orderEntityListOptional.isPresent()) {
			// 返回失败
			log.warn("合并订单订单号不存在 orderIdList={}", orderIdList);
			throw new OrderException("合并订单不存在");
		}
		List<OrderEntity> orderEntityList = orderEntityListOptional.get();
		// 校验是否和输入的订单数不匹配
		if (orderEntityList.size() != orderIdList.size()) {
			// 返回失败
			log.warn("输入的合并订单列表不正确订单size={} 传入size()={}", orderEntityList.size(), orderIdList.size());
			throw new OrderException("输入的合并订单列表不正确");
		}
		log.info("合并前的货运订单:{}", orderEntityList);
		// 遍历要合并的订单校验状态
		Long quantityTotal = 0L;
		for (OrderEntity orderEntity : orderEntityList) {
			// 校验订单状态是否正常
			this.checkOrderStatus(orderEntity.getStatus());
			quantityTotal += orderEntity.getQuantity();
		}

		// 保存合并后的订单
		OrderEntity orderEntityNew = orderRepository.save(new OrderEntity(orderEntityList.get(0).getMasterId(),
				quantityTotal, OrderStatusEnum.ORDER_CREATE.getCode(), Constants.SUB_FLAG));
		// 更新原订单为已合并
		for (OrderEntity orderEntity : orderEntityList) {
			orderEntity.setStatus(OrderStatusEnum.ORDER_MERGE.getCode());
			orderRepository.save(orderEntity);
		}

		log.info("合并的货运订单:{}", orderEntityNew);

		return orderEntityNew;
	}

	/**
	 * 变更货运订单
	 * 
	 * @param orderId
	 * @param flag
	 * @param changeNum
	 * @return
	 * @throws OrderException
	 */
	@Transactional
	@Override
	public List<OrderEntity> orderChange(Long orderId, String flag, Long changeNum) throws OrderException {
		// 查找母订单
		Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
		if (!orderEntityOptional.isPresent()) {
			// 返回失败
			log.warn("货运订单母订单号不存在 orderId={}", orderId);
			throw new OrderException("货运订单母订单号不存在");
		}
		OrderEntity orderEntity = orderEntityOptional.get();
		// 查找母订单对应的货运子订单
		Optional<List<OrderEntity>> orderEntityListOptional = orderRepository.findByMasterIdAndStatus(orderId,
				OrderStatusEnum.ORDER_CREATE.getCode());
		if (!orderEntityListOptional.isPresent()) {
			// 返回失败
			log.warn("货运子订单不存在 orderId={}", orderId);
			throw new OrderException("货运子订单不存在");
		}
		List<OrderEntity> orderEntityList = orderEntityListOptional.get();
		log.info("变更前的货运订单:{}", orderEntityList);
		// 变更后主订单货运量计算
		Long quantityOld = orderEntity.getQuantity();
		Long quantityNew = StringUtils.equals(Constants.CHANGE_FLAG_D, flag) ? orderEntity.getQuantity() + changeNum
				: orderEntity.getQuantity() - changeNum;
		if (quantityNew < 0) {
			// 返回失败
			log.warn("主订单量不足不能变更 quantityNew={}", quantityNew);
			throw new OrderException("主订单量不足不能变更");
		}
		orderEntity.setQuantity(quantityNew);
		orderRepository.save(orderEntity);
		// 按比例变更子订单
		BigDecimal rate = new BigDecimal(quantityNew).divide(new BigDecimal(quantityOld)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		List<OrderEntity> orderEntityListNew = new ArrayList<>();
		Long quantityTotal = 0L;
		int num = 0;
		for (OrderEntity orderEntity1 : orderEntityList) {
			num++;
			Long quantity1 = new BigDecimal(orderEntity1.getQuantity()).multiply(rate)
					.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			quantityTotal += quantity1;
			if (num == orderEntityList.size()) {
				if (quantityTotal != quantityNew) {
					quantity1 = quantity1 + quantityNew - quantityTotal;
				}
			}
			orderEntity1.setQuantity(quantity1);
			orderEntityListNew.add(orderEntity1);
		}
		orderRepository.saveAll(orderEntityListNew);
		log.info("变更后的货运订单:{}", orderEntityListNew);

		return orderEntityListNew;
	}

	/**
	 * 根据主订单查找对应的货运子订单
	 * 
	 * @return
	 * @throws OrderException
	 */
	@Override
	public List<OrderEntity> orderQueryByMid(Long orderId) throws OrderException {
		// 按主订单号查询子订单
		Optional<List<OrderEntity>> orderEntityListOptional = orderRepository.findByMasterIdAndStatus(orderId,
				OrderStatusEnum.ORDER_CREATE.getCode());
		if (!orderEntityListOptional.isPresent()) {
			// 返回失败
			log.warn("查询订单订单号不存在 orderId={}", orderId);
			throw new OrderException("查询订单不存在");
		}
		List<OrderEntity> orderEntityList = orderEntityListOptional.get();
		log.info("查询到该订单对应的货运子订单={}", orderEntityList);
		return orderEntityList;
	}

	/**
	 * 验证订单状态
	 * 
	 * @param status
	 * @throws OrderException
	 */
	private void checkOrderStatus(String status) throws OrderException {
		if (!StringUtils.equals(status, OrderStatusEnum.ORDER_CREATE.getCode())) {
			throw new OrderException("输入的拆分合并的订单状态不正确");
		}
	}
}
