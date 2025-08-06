package com.aeromaintenance.WorkOrder;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
    Optional<WorkOrder> findByWorkOrderNo(String workOrderNo);
    Optional<WorkOrder> findTopByWorkOrderNoStartingWithOrderByWorkOrderNoDesc(String prefix);


}

