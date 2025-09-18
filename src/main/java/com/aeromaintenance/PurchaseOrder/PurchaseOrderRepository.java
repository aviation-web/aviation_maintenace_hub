package com.aeromaintenance.PurchaseOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    // You can add custom query methods if needed
    // Example: List<PurchaseOrder> findByPoNumber(String poNumber);

    @Query("SELECT p.poNumber FROM PurchaseOrder p WHERE p.poNumber LIKE CONCAT('PO-', :year, '%') ORDER BY p.id DESC")
    List<String> findLastPoNumberForYear(@Param("year") String year);


	@Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.poNumber) from PurchaseOrder p")
	List<PurchaseOrderDTO> getAllPurchaseOrderNo();

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.partNumber, p.description) from PurchaseOrder p where p.poNumber = :poNumber")
	List<PurchaseOrderDTO> getAllPartNoByPurchaseOrders(@Param("poNumber")String poNumber);

    @Query("Select new com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO(p.description, p.currentStoke, p.unit) from PurchaseOrder p where trim(p.partNumber) = :partNumber")
	PurchaseOrderDTO getDetailsByPartNo(@Param("partNumber")String partNumber);
}
