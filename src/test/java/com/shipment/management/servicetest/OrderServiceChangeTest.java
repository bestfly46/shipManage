package com.shipment.management.servicetest;

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
public class OrderServiceChangeTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 变更货运订单
	 */
	@Test
	public void testCase() {
		try {
			List<OrderEntity> orderEntityList = orderService.orderChange(1L, "D", 20L);
			log.debug("testOrderChange=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 主订单量不足不能变更
		try {
			List<OrderEntity> orderEntityList = orderService.orderChange(1L, "C", 2000L);
			log.debug("testOrderChange=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
