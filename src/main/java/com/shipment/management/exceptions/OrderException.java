package com.shipment.management.exceptions;

/**
 * 业务异常抽象类（受检异常）
 * 
 * @author best123
 *
 */
public class OrderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4790773247213820652L;
	private final String errMsg;

	public OrderException(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
