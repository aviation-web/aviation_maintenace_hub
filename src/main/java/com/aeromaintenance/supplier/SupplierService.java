package com.aeromaintenance.supplier;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

public interface SupplierService {
	public SupplierModel saveSupplier(@RequestBody SupplierModel supplierModel); 
    public List<SupplierModel> getAllProducts();
    public SupplierModel getSupplierById(Long id);
    public SupplierModel updateSupplierModel(Long id, SupplierModel supplierModel); 
    public void deleteSupplierById(Long id);
    public List<SupplierModel> getAllPendingSupplierList(String userRole,String userName);
   // public int approveSupplier(String supplierId, String checkerBy);
	int approveSupplier(Long supplierId, String checkerBy);

}
