package com.shipment.management.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shipment.management.domain.OrderEntity;
import com.shipment.management.dto.Response;
import com.shipment.management.dto.request.OrderChangeRequest;
import com.shipment.management.dto.request.OrderCreateRequest;
import com.shipment.management.dto.request.OrderMergeRequest;
import com.shipment.management.dto.request.OrderQueryByMidRequest;
import com.shipment.management.dto.request.OrderSplitRequest;
import com.shipment.management.dto.response.OrderDto;
import com.shipment.management.exceptions.OrderException;
import com.shipment.management.service.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 主货运订单创建
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("create")
	@ResponseBody
	public Response<OrderDto> orderCreate(@RequestBody @Valid OrderCreateRequest request) {

		OrderEntity orderEntity = orderService.orderCreate(request.getQuantity());

		return new Response<OrderDto>(OrderDto.builder().orderId(orderEntity.getId())
				.quantity(orderEntity.getQuantity()).status(orderEntity.getStatus()).build());
	}

	/**
	 * 拆分货运订单
	 * 
	 * @param request
	 * @return
	 * @throws OrderException
	 */
	@RequestMapping("split")
	@ResponseBody
	public Response<List<OrderDto>> orderSplit(@RequestBody @Valid OrderSplitRequest request) throws OrderException {

		List<OrderEntity> orderEntityList = orderService.orderSplit(request.getOrderId(), request.getQuantityList());

		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		for (OrderEntity orderEntity : orderEntityList) {
			orderDtoList.add(OrderDto.builder().orderId(orderEntity.getId()).quantity(orderEntity.getQuantity())
					.status(orderEntity.getStatus()).build());
		}
		return new Response<List<OrderDto>>(orderDtoList);
	}

	/**
	 * 合并货运订单
	 * 
	 * @param request
	 * @return
	 * @throws OrderException
	 */
	@RequestMapping("merge")
	@ResponseBody
	public Response<OrderDto> orderMerge(@RequestBody @Valid OrderMergeRequest request) throws OrderException {

		OrderEntity orderEntity = orderService.orderMerge(request.getOrderIdList());

		return new Response<OrderDto>(OrderDto.builder().orderId(orderEntity.getId())
				.quantity(orderEntity.getQuantity()).status(orderEntity.getStatus()).build());
	}

	/**
	 * 货运订单变更
	 * 
	 * @param request
	 * @return
	 * @throws OrderException 
	 */

	@RequestMapping("change")
	@ResponseBody
	public Response<List<OrderDto>> orderChange(@RequestBody @Valid OrderChangeRequest request) throws OrderException {
		List<OrderEntity> orderEntityList = orderService.orderChange(request.getOrderId(), request.getFlag(),
				request.getChangeNum());

		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		for (OrderEntity orderEntity : orderEntityList) {
			orderDtoList.add(OrderDto.builder().orderId(orderEntity.getId()).quantity(orderEntity.getQuantity())
					.status(orderEntity.getStatus()).build());
		}
		return new Response<List<OrderDto>>(orderDtoList);
	}

	/**
	 * 根据主订单查找对应的货运子订单
	 * 
	 * @param request
	 * @return
	 * @throws OrderException
	 */
	@RequestMapping("queryM")
	@ResponseBody
	public Response<List<OrderDto>> orderQueryByMid(@RequestBody @Valid OrderQueryByMidRequest request)
			throws OrderException {

		List<OrderEntity> orderEntityList = orderService.orderQueryByMid(request.getOrderId());

		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		for (OrderEntity orderEntity : orderEntityList) {
			orderDtoList.add(OrderDto.builder().orderId(orderEntity.getId()).quantity(orderEntity.getQuantity())
					.status(orderEntity.getStatus()).build());
		}
		return new Response<List<OrderDto>>(orderDtoList);
	}
}
