package com.aeromaintenance.supplier;

public interface SupplierRepositoryCustom {
	int insertSupplierForm(SupplierModel form);
	boolean existsBySupplierId(Long supplierId);

}
