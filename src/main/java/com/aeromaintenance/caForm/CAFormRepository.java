package com.aeromaintenance.caForm;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aeromaintenance.WorkOrder.WorkOrder;

@Repository
public interface CAFormRepository extends JpaRepository<CAForm, String>{

	@Query("Select m.workOrderNo from WorkOrder m where m.status <> 'Closed' ")
	List<String> findAllWorkOrder();

	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(m.workOrderNo, m.description, m.partNumber, m.qty, m.snBn, m.customerName, m.repairOrderNo, m.status) " +
		       "FROM WorkOrder m WHERE m.workOrderNo = :workOrderNo")
	Optional<workOrderDetailDto> findDetailsByWorkOrder(@Param("workOrderNo") String workOrderNo);
	
	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(m.workOrderNo, m.description, m.partNumber, m.qty, m.snBn, m.customerName, m.repairOrderNo, m.status) " +
		       "FROM WorkOrder m WHERE m.status = 'IN_PROGRESS' ")
	List<workOrderDetailDto> findDetailsByWorkOrderStatus();

//	@Modifying
//    @Transactional
//    @Query("UPDATE WorkOrder i SET i.status = :status WHERE i.workOrderNo = :workOrderNo")
//    void updateWorkOrderStatus(@Param("workOrderNo") String workOrderNo,
//                               @Param("status") String status);
//
//	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.workOrderNo, w.customerName, w.repairOrderNo, c.formTrackingNumber, c.description, c.partNo, c.item, c.quantity, c.serialNo, c.status, c.remarks)" +
//		       "FROM CAForm c JOIN WorkOrder w ON c.workOrderNo = w.workOrderNo")
//	List<workOrderDetailDto> getCAAndWorkOrderDetails();
//
//	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.customerName, w.repairOrderNo)" +
//		       "FROM WorkOrder w WHERE w.workOrderNo = :workOrderNo")
//	workOrderDetailDto getCustomerOrderDetail(@Param("workOrderNo")String workOrderNo);
//
//	@Modifying
//    @Transactional
//    @Query("UPDATE CustomerOrder i SET i.status = 'Closed' WHERE i.customerName = :customerName AND i.roNo = :roNo AND i.partNo= :partNo")
//	void updateCustomerOrderStatus(@Param("customerName")String customerName, @Param("roNo")String roNo, @Param("partNo")String partNo);

	@Modifying
	@Transactional
	@Query("UPDATE WorkOrder i SET i.status = :status WHERE i.workOrderNo = :workOrderNo")
	void updateWorkOrderStatus(@Param("workOrderNo") String workOrderNo,
							   @Param("status") String status);
	
	@Query(value = "SELECT w.work_order_no AS workOrderNo, " +
	        "w.customer_name AS customerName, " +
	        "w.repair_order_no AS repairOrderNo, " +
	        "c.form_tracking_number AS formTrackingNumber, " +
	        "c.description AS description, " +
	        "c.part_no AS partNo, " +
	        "c.item AS item, " +
	        "c.quantity AS quantity, " +
	        "c.serial_no AS serialNo, " +
	        "c.status AS status, " +
	        "c.remarks AS remarks " +
	        "FROM ca_form c " +
	        "JOIN work_order w ON c.work_order_no = w.work_order_no " +
	        "WHERE STR_TO_DATE(c.date14e, '%d-%m-%Y') >= STR_TO_DATE(:date14e, '%Y-%m-%d') " +
	        "ORDER BY STR_TO_DATE(c.date14e, '%d-%m-%Y') DESC",
	        nativeQuery = true)
	List<WorkOrderDetailProjection> getCAAndWorkOrderDetails(@Param("date14e") String date14e);

//	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.workOrderNo, w.customerName, w.repairOrderNo, c.formTrackingNumber, c.description, c.partNo, c.item, c.quantity, c.serialNo, c.status, c.remarks)" +
//			"FROM CAForm c JOIN WorkOrder w ON c.workOrderNo = w.workOrderNo")
//	List<workOrderDetailDto> getCAAndWorkOrderDetails();

	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.customerName, w.repairOrderNo)" +
			"FROM WorkOrder w WHERE w.workOrderNo = :workOrderNo")
	workOrderDetailDto getCustomerOrderDetail(@Param("workOrderNo")String workOrderNo);

	@Modifying
	@Transactional
	@Query("UPDATE CustomerOrder i SET i.status = :status, i.backOrder = :backorderQty, i.quantity = :quantity WHERE i.customerName = :customerName AND i.roNo = :roNo AND i.partNo = :partNo")
	void updateCustomerOrderStatus(@Param("customerName") String customerName,
								   @Param("roNo") String roNo,
								   @Param("partNo") String partNo,
								   @Param("status") String status,
								   @Param("backorderQty") Integer backorderQty,
								   @Param("quantity") Integer quantity);
	

//    @Transactional
//    @Query("UPDATE CustomerOrder i SET i.status = 'Closed' WHERE i.customerName = :customerName AND i.roNo = :roNo AND i.partNo= :partNo")
//	void updateCustomerOrderStatus(@Param("customerName")String customerName, @Param("roNo")String roNo, @Param("partNo")String partNo);

	Optional<CAForm> findByWorkOrderNo(String workOrderNo);

	
}
