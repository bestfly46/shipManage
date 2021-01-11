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
public class OrderServiceQueryTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 根据主订单查找对应的货运子订单
	 * 
	 */
	@Test
	public void testCase() {
		try {
			List<OrderEntity> orderEntityList = orderService.orderQueryByMid(1L);
			log.debug("testOrderQueryByMid=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 订单订单号不存在
		try {
			List<OrderEntity> orderEntityList = orderService.orderQueryByMid(900L);
			log.debug("testOrderQueryByMid=" + orderEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
