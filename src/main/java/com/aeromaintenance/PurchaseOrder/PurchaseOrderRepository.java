package com.aeromaintenance.PurchaseOrder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    // You can add custom query methods if needed
    // Example: List<PurchaseOrder> findByPoNumber(String poNumber);
	
	
	@Query("Select DISTINCT new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.poNumber) from PurchaseOrder p Where p.status <> 'Close'")
	List<PurchaseOrderDTO> getAllPurchaseOrderNo();

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.partNumber, p.id) from PurchaseOrder p where p.poNumber = :poNumber AND p.orderPlacedFlag = 0 ")
	List<PurchaseOrderDTO> getAllPartNoByPurchaseOrders(@Param("poNumber")String poNumber);

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.description, p.currentStoke, p.unit, p.poDate) from PurchaseOrder p where trim(p.partNumber) = :partNumber AND trim(p.poNumber) = :poNumber AND p.id = :id")
	PurchaseOrderDTO getDetailsByPartNo(@Param("partNumber")String partNumber, @Param("poNumber")String poNumber, @Param("id")Long id);

    @Modifying
	@Query("UPDATE PurchaseRequisition i SET i.status = 'Close' WHERE i.batchNumber = :batchNumber AND i.id = :id")
    int updateStatusOfPurchaseRequisition(@Param("batchNumber")String batchNumber, @Param("id")Long id);

//	@Query("SELECT po.poNumber FROM PurchaseOrder po WHERE po.poNumber LIKE CONCAT('AMC-PO-%-', :year) ORDER BY po.id DESC")
//	String findLastPoNumberForYear(@Param("year") String year);

	// PurchaseOrderRepository.java

	@Query(value = "SELECT p.po_number FROM purchase_order p " +
			"WHERE p.po_number LIKE CONCAT('AMC-PO-%-', :yearSuffix) " +
			"ORDER BY p.po_number DESC " +
			"LIMIT 1",
			nativeQuery = true)
	Optional<String> findLastPoNumberForYear(@Param("yearSuffix") String yearSuffix);
}
