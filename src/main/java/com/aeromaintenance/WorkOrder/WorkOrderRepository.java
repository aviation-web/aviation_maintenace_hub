package com.aeromaintenance.WorkOrder;

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

    // Fetch all work order steps
    @Query("""
        SELECT DISTINCT w 
        FROM WorkOrder w
        LEFT JOIN FETCH w.workOrderSteps
    """)
    List<WorkOrder> findAllWithWorkOrderSteps();

    // Fetch all material requisitions
    @Query("""
        SELECT DISTINCT w 
        FROM WorkOrder w
        LEFT JOIN FETCH w.materialRequisitions
    """)
    List<WorkOrder> findAllWithMaterialRequisitions();

    // Fetch steps by work order number
    @Query("""
        SELECT w FROM WorkOrder w 
        LEFT JOIN FETCH w.workOrderSteps 
        WHERE w.workOrderNo = :workOrderNo
    """)
    Optional<WorkOrder> findWithStepsByWorkOrderNo(@Param("workOrderNo") String workOrderNo);

    // Fetch materials by work order number
    @Query("""
        SELECT w FROM WorkOrder w 
        LEFT JOIN FETCH w.materialRequisitions 
        WHERE w.workOrderNo = :workOrderNo
    """)
    Optional<WorkOrder> findWithMaterialsByWorkOrderNo(@Param("workOrderNo") String workOrderNo);

    // Fetch only steps by status
    @Query("""
        SELECT DISTINCT w
        FROM WorkOrder w
        LEFT JOIN FETCH w.workOrderSteps
        WHERE w.status = :status
    """)
    List<WorkOrder> findAllWithStepsByStatus(@Param("status") String status);

    // Fetch only materials by status
    @Query("""
        SELECT DISTINCT w
        FROM WorkOrder w
        LEFT JOIN FETCH w.materialRequisitions
        WHERE w.status = :status
    """)
    List<WorkOrder> findAllWithMaterialsByStatus(@Param("status") String status);
}