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
public class OrderServiceMergeTest extends SpringBootApplicationTests {

	@Autowired
	private OrderService orderService;

	/**
	 * 合并货运订单
	 * 
	 */
	@Test
	public void testCase() {
		try {
			List<Long> orderIdList = new ArrayList<>();
			orderIdList.add(3L);
			orderIdList.add(4L);
			OrderEntity orderEntity = orderService.orderMerge(orderIdList);
			log.debug("testOrderMerge=" + orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 合并订单订单号不存在
		try {
			List<Long> orderIdList = new ArrayList<>();
			orderIdList.add(300L);
			orderIdList.add(400L);
			OrderEntity orderEntity = orderService.orderMerge(orderIdList);
			log.debug("testOrderMerge=" + orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 输入的合并订单列表不正确订单
		try {
			List<Long> orderIdList = new ArrayList<>();
			orderIdList.add(1L);
			orderIdList.add(2L);
			OrderEntity orderEntity = orderService.orderMerge(orderIdList);
			log.debug("testOrderMerge=" + orderEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
