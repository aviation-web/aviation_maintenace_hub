package com.aeromaintenance.WorkOrder;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.aeromaintenance.customerOrder.CustomerOrder;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    //  Create WorkOrder
    @PostMapping
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrder workOrder) {
        WorkOrder created = workOrderService.saveWorkOrder(workOrder);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //  Get All WorkOrders
    @GetMapping
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {
        List<WorkOrder> list = workOrderService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //  Get WorkOrder by ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderById(@PathVariable Long id) {
        WorkOrder order = workOrderService.getById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //  Update WorkOrder by ID
    @PutMapping("/{id}")
    public ResponseEntity<WorkOrder> updateWorkOrder(@PathVariable Long id, @RequestBody WorkOrder updatedOrder) {
        WorkOrder updated = workOrderService.updateWorkOrder(id, updatedOrder);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //  Delete WorkOrder by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
        workOrderService.deleteWorkOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // from the customer_order_histry_table
    @GetMapping("/workordersFromChecker")
    public ResponseEntity<List<Object[]>> getPendingWorkOrders() {
        List<Object[]> pendingOrders = workOrderService.getPendingWorkOrderHistory();
        return ResponseEntity.ok(pendingOrders);
    }
}