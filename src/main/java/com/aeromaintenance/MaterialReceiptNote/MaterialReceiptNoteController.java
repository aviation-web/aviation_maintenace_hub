package com.aeromaintenance.MaterialReceiptNote;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aeromaintenance.PurchaseOrder.PurchaseOrder;
import com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO;

import java.util.List;

@RestController
@RequestMapping("/api/mrn")
public class MaterialReceiptNoteController {
    
    @Autowired
    private MaterialReceiptNoteService mrnService;
    
    
    public MaterialReceiptNoteController() {
        System.out.println("MaterialReceiptNoteController Initialized");
    }


	@PostMapping
    public ResponseEntity<MaterialReceiptNote> createMRN(@RequestBody MaterialReceiptNote mrn) {
        return ResponseEntity.ok(mrnService.createMRN(mrn));
    }
  
    
    @GetMapping
    public ResponseEntity<List<MaterialReceiptNote>> getAllMRNs() {
        return ResponseEntity.ok(mrnService.getAllMRNs());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MaterialReceiptNote> getMRNById(@PathVariable Long id) {
    	System.out.print("Edit method.."+id);
        return mrnService.getMRNById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MaterialReceiptNote> updateMRN(@PathVariable Long id, @RequestBody MaterialReceiptNote mrn) {
        return ResponseEntity.ok(mrnService.updateMRN(id, mrn));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMRN(@PathVariable Long id) {
        mrnService.deleteMRN(id);
        return ResponseEntity.ok().build();
    }
    
   
                
}
