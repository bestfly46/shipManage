package com.shipment.management.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 货运订单表
 * 
 * @author best123
 *
 */
@Entity
@Table(name = "`order`")
@Setter
@Getter
@ToString
public class OrderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -959566236481649530L;

	/**
	 * 订单号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 订单对应的主订单号
	 */
	@Column(name = "master_id", nullable = true)
	private Long masterId;

	/**
	 * 货运数量
	 */
	@Column(name = "quantity", nullable = false)
	private Long quantity;

	/**
	 * 订单状态 0：创建 1：已拆分 2：已合并
	 */
	@Column(name = "status", nullable = false, length = 1)
	private String status;

	/**
	 * 订单是否母订单 0:母订单 1：子订单
	 */
	@Column(name = "root_flag", nullable = false)
	private String rootFlag;

	public OrderEntity(Long masterId, Long quantity, String status, String rootFlag) {
		super();
		this.masterId = masterId;
		this.quantity = quantity;
		this.status = status;
		this.rootFlag = rootFlag;
	}

	public OrderEntity() {
		super();
	}
}
