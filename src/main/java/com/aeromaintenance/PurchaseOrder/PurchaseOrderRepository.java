package com.aeromaintenance.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    // You can add custom query methods if needed
    // Example: List<PurchaseOrder> findByPoNumber(String poNumber);
}
