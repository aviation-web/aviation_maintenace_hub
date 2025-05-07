package com.aeromaintenance.WorkOrder;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workorders")
@CrossOrigin
public class WorkOrderController {

    private final WorkOrderService service;

    public WorkOrderController(WorkOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WorkOrder> create(@RequestBody WorkOrder workOrder) {
        return new ResponseEntity<>(service.saveWorkOrder(workOrder), HttpStatus.CREATED);
    }

    @GetMapping
    public List<WorkOrder> getAll() {
        return service.getAllWorkOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getOne(@PathVariable Long id) {
        WorkOrder wo = service.getById(id);
        return wo != null ? ResponseEntity.ok(wo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder workOrder) {
        workOrder.setId(id); // Ensure the path ID is set to the object
        WorkOrder updated = service.saveWorkOrder(workOrder);
        return ResponseEntity.ok(updated);
    }

}
