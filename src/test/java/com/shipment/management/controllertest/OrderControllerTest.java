package com.shipment.management.controllertest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.shipment.management.dto.request.OrderChangeRequest;
import com.shipment.management.dto.request.OrderCreateRequest;
import com.shipment.management.dto.request.OrderMergeRequest;
import com.shipment.management.dto.request.OrderQueryByMidRequest;
import com.shipment.management.dto.request.OrderSplitRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller单元测试
 * 
 * @author best123
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class OrderControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void init() {
		log.info("开始测试Controller...");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();// 建议使用这种
	}

	@After
	public void after() {
		log.info("测试结束Controller...");
	}

	@Test
	public void testCreate() {
		try {
			OrderCreateRequest req = new OrderCreateRequest();
			req.setQuantity((long) 100);
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/order/create").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(JSON.toJSONString(req)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			log.debug("testCreate=" + content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSplit() {
		try {
			OrderSplitRequest req = new OrderSplitRequest();
			req.setOrderId(80L);
			List<Long> quantityList = new ArrayList<>();
			quantityList.add(50L);
			quantityList.add(50L);
			req.setQuantityList(quantityList);
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/order/split").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(JSON.toJSONString(req)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			log.debug("testCreate=" + content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMerge() {
		try {
			OrderMergeRequest req = new OrderMergeRequest();
			List<Long> orderIdList = new ArrayList<>();
			orderIdList.add(81L);
			orderIdList.add(82L);
			req.setOrderIdList(orderIdList);
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/order/merge").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(JSON.toJSONString(req)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			log.debug("testMerge=" + content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testChange() {
		try {
			OrderChangeRequest req = new OrderChangeRequest();
			req.setOrderId(80L);
			req.setFlag("D");
			req.setChangeNum(20L);
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/order/change").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(JSON.toJSONString(req)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			log.debug("testChange=" + content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOrderQueryByMid() {
		try {
			OrderQueryByMidRequest req = new OrderQueryByMidRequest();
			req.setOrderId(80L);
			MvcResult mvcResult = mockMvc
					.perform(MockMvcRequestBuilders.post("/order/queryM").contentType(MediaType.APPLICATION_JSON_UTF8)
							.content(JSON.toJSONString(req)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			log.debug("testOrderQueryByMid=" + content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
