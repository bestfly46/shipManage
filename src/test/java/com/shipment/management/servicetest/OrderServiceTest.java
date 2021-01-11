package com.shipment.management.servicetest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shipment.management.SpringBootApplicationTests;
import com.shipment.management.domain.OrderEntity;
import com.shipment.management.exceptions.OrderException;
import com.shipment.management.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service单元测试
 * 
 * @author best123
 *
 */
@Slf4j
public class OrderServiceTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 创建->拆分->合并->变根->查询子运单
	 * 
	 */
	@Test
	public void testCase() {
		// 创建 100
		OrderEntity orderEntity = null;
		try {
			orderEntity = orderService.orderCreate(100L);
			log.debug("testOrderCreate=" + orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拆分 20 30 50
		List<OrderEntity> orderEntityList = null;
		try {
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(20L);
			quantityList.add(30L);
			quantityList.add(50L);
			orderEntityList = orderService.orderSplit(orderEntity.getId(), quantityList);
			log.debug("testOrderSplit=" + orderEntityList);
		} catch (OrderException e) {
			e.printStackTrace();
		}
		// 合并30 50
		OrderEntity orderEntity1 = null;
		try {
			List<Long> orderIdList = new ArrayList<>();
			orderIdList.add(orderEntityList.get(1).getId());
			orderIdList.add(orderEntityList.get(2).getId());
			orderEntity1 = orderService.orderMerge(orderIdList);
			log.debug("testOrderMerge=" + orderEntity1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 主订单增加100
		try {
			List<OrderEntity> orderEntityList1 = orderService.orderChange(orderEntity.getId(), "D", 100L);
			log.debug("testOrderChange=" + orderEntityList1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查货运子订单
		try {
			List<OrderEntity> orderEntityList1 = orderService.orderQueryByMid(orderEntity.getId());
			log.debug("testOrderQueryByMid=" + orderEntityList1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
