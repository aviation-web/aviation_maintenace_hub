package com.aeromaintenance.MaterialRequisition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-requisitions")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MaterialRequisitionController {

    @Autowired
    private MaterialRequisitionService service;

    
    public MaterialRequisitionController() {
		System.out.println("MaterialRequisition Initilizing..");
	}

	// Add a new Material Requisition
    @PostMapping
    public MaterialRequisitionDTO addMaterialRequisition(@RequestBody MaterialRequisitionDTO dto) {
        return service.addMaterialRequisition(dto);
    }

    // Update an existing Material Requisition
    @PutMapping("/{id}")
    public MaterialRequisitionDTO updateMaterialRequisition(@PathVariable Long id, @RequestBody MaterialRequisitionDTO dto) {
        return service.updateMaterialRequisition(id, dto);
    }

    // Delete a Material Requisition by ID
    @DeleteMapping("/{id}")
    public String deleteMaterialRequisition(@PathVariable Long id) {
        service.deleteMaterialRequisition(id);
        return "Material Requisition with ID " + id + " deleted successfully.";
    }

    // Get a Material Requisition by ID
    @GetMapping("/{id}")
    public MaterialRequisitionDTO getMaterialRequisitionById(@PathVariable Long id) {
        return service.getMaterialRequisitionById(id);
    }

    // Get all Material Requisitions
    @GetMapping
    public List<MaterialRequisitionDTO> getAllMaterialRequisitions() {
        return service.getAllMaterialRequisitions();
    }
}
