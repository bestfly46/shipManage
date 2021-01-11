package com.shipment.management.servicetest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shipment.management.SpringBootApplicationTests;
import com.shipment.management.domain.OrderEntity;
import com.shipment.management.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service单元测试
 * 
 * @author best123
 *
 */
@Slf4j
public class OrderServiceSplitTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 拆分货运订单
	 * 
	 */
	@Test
	public void testCase() {
		try {
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(20L);
			quantityList.add(30L);
			quantityList.add(50L);
			List<OrderEntity> orderEntityList = orderService.orderSplit(1L, quantityList);
			log.debug("testOrderSplit=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 订单状态不正确
		try {
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(20L);
			quantityList.add(30L);
			quantityList.add(50L);
			List<OrderEntity> orderEntityList = orderService.orderSplit(1L, quantityList);
			log.debug("testOrderSplit=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拆分订单订单号不存在
		try {
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(20L);
			quantityList.add(30L);
			quantityList.add(50L);
			List<OrderEntity> orderEntityList = orderService.orderSplit(600L, quantityList);
			log.debug("testOrderSplit=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拆分订单传入的金额不正确
		try {
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(20L);
			quantityList.add(30L);
			quantityList.add(40L);
			List<OrderEntity> orderEntityList = orderService.orderSplit(1L, quantityList);
			log.debug("testOrderSplit=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
