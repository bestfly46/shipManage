package com.shipment.management.intercept;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shipment.management.constants.Constants;
import com.shipment.management.dto.Response;
import com.shipment.management.exceptions.OrderException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author best123
 *
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class OrderExceptionInterceptor {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	public Response handleException(Exception exception) {

		log.error("Handle exception: {}", exception);

		return new Response(Constants.CODE_FAIL, Constants.MSG_FAIL);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(OrderException.class)
	public Response handleOrderException(OrderException exception) {

		log.error("Handle OrderException: {}", exception.getErrMsg());

		return new Response(Constants.CODE_FAIL, exception.getErrMsg());
	}
}
