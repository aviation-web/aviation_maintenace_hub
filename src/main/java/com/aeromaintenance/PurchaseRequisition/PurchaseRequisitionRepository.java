package com.aeromaintenance.PurchaseRequisition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {

    @Query("SELECT new com.aeromaintenance.PurchaseRequisition.PartInfoDTO(p.partNumber, p.description) FROM PurchaseRequisition p")
    List<PartInfoDTO> findAllPartNumbersAndDescriptions();

    List<PurchaseRequisition> findByBatchNumber(String batchNumber);
}