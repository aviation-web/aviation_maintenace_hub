package com.aeromaintenance.PurchaseOrder;

import java.util.List;

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

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.partNumber, p.description) from PurchaseOrder p where p.poNumber = :poNumber")
	List<PurchaseOrderDTO> getAllPartNoByPurchaseOrders(@Param("poNumber")String poNumber);

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.description, p.currentStoke, p.unit) from PurchaseOrder p where trim(p.partNumber) = :partNumber AND trim(p.poNumber) = :poNumber")
	PurchaseOrderDTO getDetailsByPartNo(@Param("partNumber")String partNumber, @Param("poNumber")String poNumber);

    @Modifying
	@Query("UPDATE PurchaseRequisition i SET i.status = 'Close' WHERE i.batchNumber = :batchNumber AND i.id = :id")
    int updateStatusOfPurchaseRequisition(@Param("batchNumber")String batchNumber, @Param("id")Long id);
    
}
