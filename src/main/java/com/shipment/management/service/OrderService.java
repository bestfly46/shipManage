package com.shipment.management.service;

import java.util.List;

import com.shipment.management.domain.OrderEntity;
import com.shipment.management.exceptions.OrderException;

public interface OrderService {

	/**
	 * 创建货运订单
	 * 
	 * @param quantity
	 * @return
	 */
	public OrderEntity orderCreate(Long quantity);

	/**
	 * 拆分货运订单
	 * 
	 * @param orderId
	 * @param quantityList
	 * @return
	 * @throws OrderException
	 */
	public List<OrderEntity> orderSplit(Long orderId, List<Long> quantityList) throws OrderException;

	/**
	 * 合并货运订单
	 * 
	 * @param orderIdList
	 * @return
	 * @throws OrderException
	 */
	public OrderEntity orderMerge(List<Long> orderIdList) throws OrderException;

	/**
	 * 变更货运订单
	 * 
	 * @param orderId
	 * @param flag
	 * @param changeNum
	 * @return
	 * @throws OrderException
	 */
	public List<OrderEntity> orderChange(Long orderId, String flag, Long changeNum) throws OrderException;

	/**
	 * 根据主订单查找对应的货运子订单
	 * 
	 * @return
	 * @throws OrderException
	 */
	public List<OrderEntity> orderQueryByMid(Long orderId) throws OrderException;
}
