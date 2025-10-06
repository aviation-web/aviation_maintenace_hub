package com.aeromaintenance.MaterialRequisition;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRequisitionRepository extends JpaRepository<MaterialRequisition, Long> {
    // You can add custom queries here if needed
    @Query(value = "SELECT material_requisition_no FROM material_requisition ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLastMaterialRequisitionNo();

    @Query("SELECT m FROM MaterialRequisition m WHERE m.status = 'Open'")
    List<MaterialRequisition> findAllActive();

}
