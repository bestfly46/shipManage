package com.shipment.management.enums;

/**
 * 订单支付状态
 *
 * @author best123
 */
public enum OrderStatusEnum {

	ORDER_CREATE("0", "订单正常"),
	ORDER_SPLIT("1", "订单已拆分"),
	ORDER_MERGE("2", "订单已合并");

	private final String code;
	private final String msg;
	
	private OrderStatusEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
