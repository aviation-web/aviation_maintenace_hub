package com.aeromaintenance.CustomerRepairProduct;

import com.common.CustomerRepairDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepairProductRepository extends JpaRepository<CustomerRepairProduct, Long> {

    @Query("SELECT new com.common.CustomerRepairDTO(cp.productName, cp.productDescription, cp.unitOfMeasurement) FROM CustomerRepairProduct cp")
    List<CustomerRepairDTO> findAllProductNameAndDescriptionDTO();

}