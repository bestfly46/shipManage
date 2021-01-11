package com.shipment.management.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * 根据主订单查找对应的货运子订单请求
 * 
 * @author best123
 *
 */
@Data
public class OrderQueryByMidRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 667068568655186658L;

	@NotNull(message = "货运订单不能为空")
	@Range(min = 1, max = 999999999, message = "货运订单在1到999999999之间")
	private Long orderId;
}
