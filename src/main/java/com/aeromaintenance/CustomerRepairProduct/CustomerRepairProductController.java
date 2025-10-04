package com.aeromaintenance.CustomerRepairProduct;


import com.aeromaintenance.CustomerRepairProduct.CustomerRepairProduct;
import com.aeromaintenance.CustomerRepairProduct.CustomerRepairProductService;
import com.common.CustomerRepairDTO;
import com.common.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repair-products")
//@CrossOrigin(origins = "http://localhost:3000")
public class CustomerRepairProductController {

    private final CustomerRepairProductService service;

    public CustomerRepairProductController(CustomerRepairProductService service) {
        this.service = service;
        System.out.println("CustomerRepairProductController Initilizing..");

    }

    @PostMapping
    public ResponseEntity<CustomerRepairProduct> create(@RequestBody CustomerRepairProduct product) {
        return ResponseEntity.ok(service.save(product));
    }

    @GetMapping
    public ResponseEntity<List<CustomerRepairProduct>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRepairProduct> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRepairProduct> update(@PathVariable Long id, @RequestBody CustomerRepairProduct product) {
        return ResponseEntity.ok(service.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Fetch Product Name and Product Desc
    @GetMapping("/prodName/{name}")
    public ResponseEntity<List<CustomerRepairDTO>> getPartNumPartDesc(){
        return ResponseEntity.ok(service.getProdNumProdDesc());
    }

}
