package com.aeromaintenance.CustomerRepairProduct;

import com.common.CustomerRepairDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepairProductRepository extends JpaRepository<CustomerRepairProduct, Long> {

}
