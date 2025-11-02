package com.aeromaintenance.caForm;

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
		       "FROM WorkOrder m WHERE m.status <> 'Closed' ")
	List<workOrderDetailDto> findDetailsByWorkOrderStatus();

	@Modifying
    @Transactional
    @Query("UPDATE WorkOrder i SET i.status = :status WHERE i.workOrderNo = :workOrderNo")
    void updateWorkOrderStatus(@Param("workOrderNo") String workOrderNo, 
                               @Param("status") String status);

	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.workOrderNo, w.customerName, w.repairOrderNo, c.formTrackingNumber, c.description, c.partNo, c.item, c.quantity, c.serialNo, c.status, c.remarks)" +
		       "FROM CAForm c JOIN WorkOrder w ON c.workOrderNo = w.workOrderNo")
	List<workOrderDetailDto> getCAAndWorkOrderDetails();

	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(w.customerName, w.repairOrderNo)" +
		       "FROM WorkOrder w WHERE w.workOrderNo = :workOrderNo")
	workOrderDetailDto getCustomerOrderDetail(@Param("workOrderNo")String workOrderNo);

	@Modifying
    @Transactional
    @Query("UPDATE CustomerOrder i SET i.status = 'Closed' WHERE i.customerName = :customerName AND i.roNo = :roNo AND i.partNo= :partNo")
	void updateCustomerOrderStatus(@Param("customerName")String customerName, @Param("roNo")Long long1, @Param("partNo")String partNo);
}
