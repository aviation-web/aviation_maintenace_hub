package com.aeromaintenance.PurchaseRequisition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRequisitionRepository extends JpaRepository<PurchaseRequisition, Long> {
}