package com.aeromaintenance.supplier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository  extends JpaRepository<SupplierModel, Long>{
	// Fetch suppliers where userAction = "M" and userRole = "QM"
    List<SupplierModel> findByUserActionAndUserRole(String userAction, String userRole);
    
}
