package com.shipment.management.constants;

/**
 * 常量
 * 
 * @author best123
 *
 */
public interface Constants {
	/**
	 * 成功失败标识
	 */
	int CODE_SUCCESS = 0;
	int CODE_FAIL = -1;

	String MSG_SUCCESS = "OK";
	String MSG_FAIL = "FAIL";

	/**
	 * 根节点标识
	 */
	String ROOT_FLAG = "0";
	String SUB_FLAG = "1";
	
	/**
	 * 变更标识 D:增加 C:减少
	 */
	String CHANGE_FLAG_D = "D";
	String CHANGE_FLAG_C = "C";
}
