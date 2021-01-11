package com.shipment.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipment.management.domain.OrderEntity;

/**
 * 货运子订单表操作
 * 
 * @author best123
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

	/**
	 * 查找订单列表
	 * 
	 * @param orderIdList
	 * @return
	 */
	Optional<List<OrderEntity>> findByIdIn(List<Long> orderIdList);

	/**
	 * 根据主订单号查找记录
	 * 
	 * @param masterId
	 * @param status
	 * @return
	 */
	Optional<List<OrderEntity>> findByMasterIdAndStatus(Long masterId, String status);
}
