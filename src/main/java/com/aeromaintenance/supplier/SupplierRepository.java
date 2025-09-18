package com.aeromaintenance.supplier;

import java.util.List;

import javax.transaction.Transactional;

import com.common.SupplierNameDto;
import com.common.SupplierNamePaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository  extends JpaRepository<SupplierModel, Long> {
	// Fetch suppliers where userAction = "1" and userRole = "M"
	List<SupplierModel> findByUserRoleAndUserAction(String userRole, String userAction);

    @Query("SELECT new com.common.SupplierNamePaymentDTO(s.supplierName, s.paymentTerms) " +
            "FROM SupplierModel s")
    List<SupplierNamePaymentDTO> findSupplierNamesAndPaymentTerms();
    
    @Query("SELECT s.supplierName FROM SupplierModel s")
	List<String> findAllSupplierNames();
}
