package com.aeromaintenance.MaterialRequisition;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRequisitionRepository extends JpaRepository<MaterialRequisition, Long> {
    // You can add custom queries here if needed
}
