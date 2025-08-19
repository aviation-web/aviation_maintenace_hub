package com.aeromaintenance.WorkOrder;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
    Optional<WorkOrder> findByWorkOrderNo(String workOrderNo);
    Optional<WorkOrder> findTopByWorkOrderNoStartingWithOrderByWorkOrderNoDesc(String prefix);
    
    @Query("""
            SELECT DISTINCT w 
            FROM WorkOrder w
            LEFT JOIN FETCH w.workOrderSteps
        """)
        List<WorkOrder> findAllWithWorkOrderSteps();

        @Query("""
            SELECT DISTINCT w 
            FROM WorkOrder w
            LEFT JOIN FETCH w.materialRequisitions
        """)
        List<WorkOrder> findAllWithMaterialRequisitions();
        
        @Query("SELECT w FROM WorkOrder w LEFT JOIN FETCH w.workOrderSteps WHERE w.workOrderNo = :workOrderNo")
        Optional<WorkOrder> findWithStepsByWorkOrderNo(@Param("workOrderNo") String workOrderNo);

        @Query("SELECT w FROM WorkOrder w LEFT JOIN FETCH w.materialRequisitions WHERE w.workOrderNo = :workOrderNo")
        Optional<WorkOrder> findWithMaterialsByWorkOrderNo(@Param("workOrderNo") String workOrderNo);
    }