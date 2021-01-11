package com.shipment.management.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

/**
 * 拆分货运订单请求
 * 
 * @author best123
 *
 */
@Data
public class OrderSplitRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 667068568655186658L;

	@NotNull(message = "货运订单不能为空")
	@Range(min = 1, max = 999999999, message = "货运订单在1到999999999之间")
	private Long orderId;

	@NotNull(message = "货运拆分数量列表不能为空")
	private List<Long> quantityList;
}
