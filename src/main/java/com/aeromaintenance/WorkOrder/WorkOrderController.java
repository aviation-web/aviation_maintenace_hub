package com.aeromaintenance.WorkOrder;

import com.aeromaintenance.customerOrder.CustomerOrder;
import com.aeromaintenance.customerOrder.CustomerOrderHistoryDTO;
import com.aeromaintenance.customerOrder.CustomerOrderShortDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        WorkOrder savedOrder = workOrderService.createWorkOrder(dto);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<WorkOrder>> getAllWorkOrders() {
        return ResponseEntity.ok(workOrderService.getAllWorkOrdersWithDetails());
    }

    @GetMapping("/{workOrderNo}")
    public ResponseEntity<WorkOrder> getWorkOrderByNo(@PathVariable String workOrderNo) {
        return ResponseEntity.ok((WorkOrder) workOrderService.getWorkOrderByNo(workOrderNo));
    }

    @PutMapping("/{workOrderNo}")
    public ResponseEntity<WorkOrder> updateWorkOrder(@PathVariable String workOrderNo,
                                                     @RequestBody WorkOrderDTO dto) {
        return ResponseEntity.ok(workOrderService.updateWorkOrder(workOrderNo, dto));
    }

    @DeleteMapping("/{workOrderNo}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable String workOrderNo) {
        workOrderService.deleteWorkOrder(workOrderNo);
        return ResponseEntity.noContent().build();
    }

//    // from the customer_order_histry_table
//    @GetMapping("/workordersFromChecker")
//    public ResponseEntity<List<CustomerOrderHistoryDTO>> getPendingWorkOrders() {
//        List<CustomerOrderHistoryDTO> pendingOrders = workOrderService.getPendingWorkOrderHistory();
//        return ResponseEntity.ok(pendingOrders);
//    }

    @GetMapping("/workorders-short/{srNo}")
    public ResponseEntity<?> getShortWorkOrderBySrNo(@PathVariable String srNo) {
        CustomerOrderShortDTO dto = workOrderService.getShortOrderBySrNo(srNo);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No order found with sr_no = " + srNo);
        }

        return ResponseEntity.ok(dto);
    }


    @GetMapping("/workordersFromChecker")
    public ResponseEntity<List<CustomerOrder>> getAllCustomerOrders() {
        List<CustomerOrder> orders = workOrderService.getAllCustomerOrders();
        return ResponseEntity.ok(orders);
    }

    //  New - fetch one record by srNo
    @GetMapping("/workordersFromChecker/{srNo}")
    public ResponseEntity<?> getCustomerOrderBySrNo(@PathVariable String srNo) {
        CustomerOrder order = workOrderService.getCustomerOrderBySrNo(srNo);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Customer Order found with srNo = " + srNo);
        }

        return ResponseEntity.ok(order);
    }

    // Get only OPEN status Customer Orders
    @GetMapping("/open")
    public ResponseEntity<List<CustomerOrder>> getOpenCustomerOrders() {

        List<CustomerOrder> openOrders = workOrderService.getCustomerOrdersByStatus("OPEN");
        List<CustomerOrder> partialOrders = workOrderService.getCustomerOrdersByStatus("PARTIAL");
        List<CustomerOrder> allOrders = new ArrayList<>();
        allOrders.addAll(openOrders);
        allOrders.addAll(partialOrders);

        if (allOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<WorkOrder>> getClosedWorkOrders() {
        List<WorkOrder> closedOrders = workOrderService.getClosedWorkOrders();

        if (closedOrders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(closedOrders);
    }
}
