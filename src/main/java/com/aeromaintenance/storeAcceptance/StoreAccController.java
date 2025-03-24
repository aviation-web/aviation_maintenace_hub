package com.aeromaintenance.storeAcceptance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storeAcceptance")
@CrossOrigin(origins = "http://localhost:8089")
public class StoreAccController {

    public StoreAccController() {
System.out.println("StoreAccController Initialized");
    
    }

	@Autowired
    private StoreAccService service;

    @GetMapping
    public List<StoreAcc> getAllStoreAcceptances() {
        return service.getAllStoreAcceptances();
    }

    @GetMapping("/{id}")
    public StoreAcc getStoreAcceptanceById(@PathVariable String id) {
        return service.getStoreAcceptanceById(id);
    }

    @PostMapping
    public StoreAcc saveStoreAcceptance(@RequestBody StoreAcc storeAcceptance) {
        return service.saveStoreAcceptance(storeAcceptance);
    }

    @PutMapping("/{id}")
    public StoreAcc updateStoreAcceptance(@PathVariable String id, @RequestBody StoreAcc storeAcceptance) {
        return service.updateStoreAcceptance(id, storeAcceptance);
    }

//    @DeleteMapping("/{id}")
//    public String deleteStoreAcceptance(@PathVariable Long id) {
//        service.deleteStoreAcceptance(id);
//        return "Record deleted successfully!";
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStoreAcceptance(@PathVariable String id) {
      service.deleteStoreAcceptance(id);
        return ResponseEntity.ok("Deleted storeAcceptance with ID: " + id);
    }
}
