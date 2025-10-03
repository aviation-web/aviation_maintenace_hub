package com.aeromaintenance.PurchaseRequisition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {
    
    @Query("SELECT new com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO(p.id,p.partNumber, p.description) FROM PurchaseRequisition p")
    List<PurchaseRequisitionDTO> findAllPartNumbersAndDescriptions();

    List<PurchaseRequisition> findByBatchNumber(String batchNumber);

    List<PurchaseRequisition> findByStatus(String status);

//    @Query("SELECT new com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO(p.srNo,p.partNumber, p.description, p.currentStock) FROM PurchaseRequisition p where p.flag = Pending ")
//    List<PurchaseRequisitionDTO> findAllOrderByBatchNumber();

}