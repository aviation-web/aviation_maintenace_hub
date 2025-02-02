package com.aeromaintenance.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeromaintenance.product.Product;


@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
	
	@Autowired
    private SupplierService supplierService;
	
	public SupplierController() {
	 System.out.println("Supplier Register Working....");
 }
	@Validated
	@PostMapping("/supplierReg")
	public ResponseEntity<SupplierModel> addSupplier(@RequestBody SupplierModel supplierModel) {
        SupplierModel savedSupplier = supplierService.saveSupplier(supplierModel);
        return ResponseEntity.ok(savedSupplier);
    }
	
	// Get All Products
    @GetMapping
    public ResponseEntity<List<SupplierModel>> getAllProducts() {
        return ResponseEntity.ok(supplierService.getAllProducts());
    }
    
    
 // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplierModel> getProductById(@PathVariable Long id) {
    	SupplierModel supplierModel = supplierService.getSupplierById(id);
        if (supplierModel != null) {
            return ResponseEntity.ok(supplierModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // Delete Product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
    	SupplierModel supplierModel = supplierService.getSupplierById(id);
        if (supplierModel != null) {
        	supplierService.deleteSupplierById(id);
            return ResponseEntity.ok("Supplier  deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
     // Update Product by ID
    @PutMapping("/{id}")
    public ResponseEntity<SupplierModel> updateSupplierById(@PathVariable Long id, @RequestBody SupplierModel updateSupplierModel) {
    	SupplierModel supplierModel = supplierService.updateSupplierModel(id, updateSupplierModel);
        if (supplierModel != null) {
            return ResponseEntity.ok(supplierModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
