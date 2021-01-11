package com.shipment.management.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 订单信息
 * 
 * @author best123
 *
 */
@Setter
@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 908146162800448802L;

	/**
	 * 订单号
	 */
	private Long orderId;

	/**
	 * 货运数量
	 */
	private Long quantity;

	/**
	 * 订单状态 0：创建 1：已拆分 2：已合并
	 */
	private String status;
}
