package com.shipment.management.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * 变更货运订单请求
 * 
 * @author best123
 *
 */
@Data
public class OrderChangeRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 667068568655186658L;

	@NotNull(message = "货运订单不能为空")
	@Range(min = 1, max = 999999999, message = "货运订单在1到999999999之间")
	private Long orderId;

	@NotBlank(message = "变更标识不能为空")
	@Pattern(regexp = "^D|C$", message = "变更标识只能为D或者C")
	private String flag;

	@NotNull(message = "变更货运量不能为空")
	@Range(min = 1, max = 9999, message = "货运订单在1到9999之间")
	private Long changeNum;
}
