package com.shipment.management.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * 主装运订单创建请求
 * 
 * @author best123
 *
 */
@Data
public class OrderCreateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 667068568655186658L;

	@NotNull(message = "货运数量不能为空")
	@Range(min = 1, max = 999999, message = "货运数量是数字在1到999999之间")
	private Long quantity;
}
