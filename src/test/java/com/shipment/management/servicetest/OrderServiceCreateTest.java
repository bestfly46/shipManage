package com.shipment.management.servicetest;

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
public class OrderServiceCreateTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 创建货运订单
	 */
	@Test
	public void testCase() {
		try {
			OrderEntity orderEntity = orderService.orderCreate(100L);
			log.debug("testOrderCreate=" + orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
