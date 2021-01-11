package com.shipment.management.dto.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 合并货运订单请求
 * 
 * @author best123
 *
 */
@Data
public class OrderMergeRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 667068568655186658L;

	@NotNull(message = "货运合并订单列表不能为空")
	private List<Long> orderIdList;
}
