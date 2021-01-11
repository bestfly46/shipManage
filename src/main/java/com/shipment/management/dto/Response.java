package com.shipment.management.dto;

import com.shipment.management.constants.Constants;

import lombok.Data;
import lombok.NonNull;

/**
 * 返回对象
 * 
 * @author best123
 *
 * @param <T>
 */
@Data
public class Response<T> {

	public static Response SUCCESS = new Response(Constants.CODE_SUCCESS, Constants.MSG_SUCCESS);

	@NonNull
	int code;

	String message;

	@NonNull
	T result;

	public Response(T result) {
		this.result = result;
		this.code = Constants.CODE_SUCCESS;
		this.message = Constants.MSG_SUCCESS;
	}

	public Response(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
