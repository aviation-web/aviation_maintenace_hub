package com.aeromaintenance.WorkOrder;

import com.aeromaintenance.customerOrder.CustomerOrderHistoryDTO;
import com.aeromaintenance.customerOrder.CustomerOrderShortDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping
    public ResponseEntity<String> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        return ResponseEntity.ok(workOrderService.createWorkOrder(dto));
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

    // from the customer_order_histry_table
    @GetMapping("/workordersFromChecker")
    public ResponseEntity<List<CustomerOrderHistoryDTO>> getPendingWorkOrders() {
        List<CustomerOrderHistoryDTO> pendingOrders = workOrderService.getPendingWorkOrderHistory();
        return ResponseEntity.ok(pendingOrders);
    }

    @GetMapping("/workorders-short/{srNo}")
    public ResponseEntity<?> getShortWorkOrderBySrNo(@PathVariable String srNo) {
        CustomerOrderShortDTO dto = workOrderService.getShortOrderBySrNo(srNo);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No order found with sr_no = " + srNo);
        }

        return ResponseEntity.ok(dto);
    }


}
