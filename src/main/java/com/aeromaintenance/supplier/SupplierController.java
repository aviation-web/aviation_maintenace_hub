package com.aeromaintenance.supplier;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.ResponseBean;



@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
	
	@Autowired
    private SupplierService supplierService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public SupplierController() {
	 System.out.println("Supplier Register Working....");
 }
	
	
	@PostMapping("/supplierReg")
	public ResponseEntity<ResponseBean<Void>> addSupplier(@Valid@RequestBody SupplierDto supplierDto) {
	   SupplierModel supplierModel = modelMapper.map(supplierDto, SupplierModel.class);
	   supplierService.saveSupplier(supplierModel);
	   ResponseBean<Void> responseBean = new ResponseBean<>("200", "Supplier added successfully", null);
	    return ResponseEntity.ok(responseBean);
    }
	
	 @GetMapping("/getPendingSupplierList")
	    public ResponseEntity<List<SupplierModel>> getPendingSupplierList(
	            @RequestParam(required = false) String userAction,
	            @RequestParam(required = false) String userRole) {
	        
	        List<SupplierModel> suppliers = supplierService.getAllPendingSupplierList(userAction, userRole);
	        return ResponseEntity.ok(suppliers);
	    }
	 
	 @PostMapping("/approve/{supplierId}")
	    public ResponseEntity<ResponseBean<Void>> approveSupplier(
	            @PathVariable Long supplierId,
	            @RequestParam String checkerBy) {
	        
	        int rowsInserted = supplierService.approveSupplier(supplierId, checkerBy);
	        
	        if (rowsInserted == 1) {
	            ResponseBean<Void> response = new ResponseBean<>("200", "Supplier approved and moved to history successfully", null);
	            return ResponseEntity.ok(response);
	        } else {
	            ResponseBean<Void> response = new ResponseBean<>("500", "Failed to move supplier to history", null);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
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
