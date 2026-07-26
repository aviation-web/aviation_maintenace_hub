package com.aeromaintenance.PurchaseOrder;

import com.common.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aeromaintenance.PurchaseRequisition.PurchaseRequisition;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    public PurchaseOrderController() {
    		System.out.println("PurchaseOrder Initilizing....");
		// TODO Auto-generated constructor stub
	}

	@Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired 
    private PurchaseRequisitionService purchaseRequisitionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseBean<Void>> createPurchaseOrder(@RequestBody PurchaseOrderDTO request) {

//        List<PurchaseRequisition> requisitions =
//                purchaseRequisitionService.getRequisitionsByBatchNo(request.getBatchNumber());

//        System.out.println("BatchNumber:- " + request.getBatchNumber());
//        System.out.println("Fetched Purchase Requisition Records for BatchNo: " + request.getBatchNumber());
//        for (PurchaseRequisition req : requisitions) {
//            System.out.println("SrNo: " + req.getId() +
//                    ", CurrentStock: " + req.getCurrentStock() +
//                    ", PartNumber: " + req.getPartNumber() +
//                    ", Description: " + req.getDescription());
//        }
//
//        if (requisitions.isEmpty()) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new ResponseBean<>("400", "Invalid Batch Number", null));
//        }
    	
    	if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseBean<>("400", "No items provided", null));
        }

//       String poNumber = "PO_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        // Generate PO number
        String poNumber = purchaseOrderService.getNextPONumber();

        for (PurchaseOrderItem req : request.getItems()) {
            PurchaseOrder order = new PurchaseOrder();
            order.setSrNo(Math.toIntExact(req.getId()));
            order.setCurrentStoke(req.getRequiredQty());
            order.setPartNumber(req.getPartNumber());
            order.setDescription(req.getDescription());
            order.setGrossOfSingle(req.getGross());
            order.setPoNumber(poNumber);
            order.setPoDate(request.getPoDate());
            order.setOurReference(request.getOurReference());
            order.setYourReference(request.getYourReference());
            order.setDelivery(request.getDelivery());
            order.setDeliveryAddress(request.getDeliveryAddress());
            order.setPaymentTerms(request.getPaymentTerms());
            order.setAddress(request.getAddress());
            order.setSupplierName(request.getSupplierName());

            order.setUnit(req.getUnits());
            order.setRatePerUnit(req.getRate());
            order.setGrossAmount(request.getGrossAmount());

            order.setIgstPercentage(request.getIgstPercentage());
            order.setCgstPercentage(request.getCgstPercentage());
            order.setSgstPercentage(request.getSgstPercentage());
            order.setSgst(request.getSgst());
            order.setCgst(request.getCgst());
            order.setIgst(request.getIgst());
            order.setTotal(request.getTotal());
            order.setGrandTotal(request.getGrandTotal());
            order.setPF(request.getPF());
            order.setTransportation(request.getTransportation());
            order.setOther_Charges(request.getOther_Charges());
            order.setInsurance(request.getInsurance());

            order.setTermsAndConditions(request.getTermsAndConditions());
            order.setIncoterm(request.getIncoterm());
            order.setCurrency(request.getCurrency());
            order.setForwarder(request.getForwarder());

            purchaseOrderService.saveOrder(order);
            purchaseOrderService.updateStatusOfPurchaseRequisition(req.getBatchNumber(), req.getId());
        }

        return ResponseEntity
                .ok(new ResponseBean<>("200", "Purchase Orders Created Successfully", null));
    }

    @GetMapping("/requisitions-by-batch/{batchNumber}")
	    public ResponseEntity<List<PurchaseRequisitionDTO>> getRequisitionsByBatch(@PathVariable String batchNumber) {
	        List<PurchaseRequisitionDTO> list = purchaseOrderService.getRequisitionsByBatchNumber(batchNumber);
	        return ResponseEntity.ok(list);
	    }

    // Update an existing Purchase Order
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(
            @PathVariable Long id,
            @RequestBody PurchaseOrderDTO updatedPurchaseOrderDTO) {
        PurchaseOrder updatedPurchaseOrder = purchaseOrderService.updatePurchaseOrder(id, updatedPurchaseOrderDTO);
        if (updatedPurchaseOrder != null) {
            return new ResponseEntity<>(updatedPurchaseOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if the Purchase Order is not found
        }
    }

    // Delete a Purchase Order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 on successful deletion
    }

    // View a Purchase Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> viewPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.viewPurchaseOrderById(id);
        if (purchaseOrder != null) {
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if the Purchase Order is not found
        }
    }

    // View all Purchase Orders
    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> viewAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderService.viewAllPurchaseOrders();
        return new ResponseEntity<>(purchaseOrders, HttpStatus.OK);
    }
    
    @GetMapping("/getAllPurchaseOrder")
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrderNo(){
    	return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrderNo());
    }
    
    @GetMapping("/getAllPartNoByPurchaseOrder/{poNumber}")
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPartNoByPurchaseOrder(@PathVariable String poNumber){
    	return ResponseEntity.ok(purchaseOrderService.getAllPartNoByPurchaseOrders(poNumber));
    }
    
    @GetMapping("/getDetailByPartNo/{partNumber}")
    public ResponseEntity<PurchaseOrderDTO> getDetailsByPartNo(@PathVariable String partNumber,
            @RequestParam String poNumber){
    	return ResponseEntity.ok(purchaseOrderService.getDetailsByPartNo(partNumber.trim(),poNumber.trim()));
    }
    
}
