package com.aeromaintenance.PurchaseRequisition;

import com.common.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchase-requisitions")
public class PurchaseRequisitionController {
    @Autowired
    private PurchaseRequisitionService service;

    public PurchaseRequisitionController(){
    	System.out.println("PurchaseRequisitionController Initilizing..");
    }
    @PostMapping
    public ResponseEntity<PurchaseRequisitionDTO> createPurchaseRequisition(@RequestBody PurchaseRequisitionDTO dto) {
        return ResponseEntity.ok(service.createPurchaseRequisition(dto));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseRequisitionDTO>> getAllPurchaseRequisitions() {
        return ResponseEntity.ok(service.getAllPurchaseRequisitions());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequisitionDTO> viewPurchaseRequisition(@PathVariable Long id) {
        return ResponseEntity.ok(service.viewPurchaseRequisitionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseRequisitionDTO> updatePurchaseRequisition(
        @PathVariable Long id, 
        @RequestBody PurchaseRequisitionDTO dto
    ) {
        return ResponseEntity.ok(service.updatePurchaseRequisition(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseRequisition(@PathVariable Long id) {
        service.deletePurchaseRequisition(id);
        return ResponseEntity.ok().build();
    }

    // Export Functionality
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportPurchaseRequisitions(
        @RequestParam(value = "format", defaultValue = "CSV") String format
    ) {
        List<PurchaseRequisitionDTO> requisitions = service.getAllPurchaseRequisitions();
        
        byte[] exportData;
        String contentType;
        String filename;

        switch (format.toUpperCase()) {
            case "XLS":
                exportData = generateExcelFile(requisitions);
                contentType = "application/vnd.ms-excel";
                filename = "purchase_requisitions.xls";
                break;
            case "PDF":
                exportData = generatePdfFile(requisitions);
                contentType = "application/pdf";
                filename = "purchase_requisitions.pdf";
                break;
            case "TXT":
                exportData = generateTextFile(requisitions);
                contentType = "text/plain";
                filename = "purchase_requisitions.txt";
                break;
            case "CSV":
            default:
                exportData = generateCsvFile(requisitions);
                contentType = "text/csv";
                filename = "purchase_requisitions.csv";
        }

        return ResponseEntity.ok()
            .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(exportData);
    }

    @PostMapping("/batch")
    public ResponseEntity<ResponseBean<Void>> saveBatch(@RequestBody List<PurchaseRequisitionDTO> requisitionDTOs) {
        List<PurchaseRequisition> entities = requisitionDTOs.stream()
                .map(PurchaseRequisitionDTO::toEntity)
                .collect(Collectors.toList());

        if(entities.size()<=10 && !entities.isEmpty()) {
            service.saveInBatches(entities, entities.size());
            ResponseBean<Void> response = new ResponseBean<>("200","Saved in batche " + entities.size() + " successfully.",null);
            return ResponseEntity.ok(response);
        }else {
            ResponseBean<Void> response = new ResponseBean<>("401","At a time 10 records can be added only Your Records count is :-  " + entities.size() ,null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Export CSV file to disk
    @GetMapping("/csv")
    public ResponseEntity<String> exportToCSV() {
        String result = service.exportToCSV();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/batch/{batchNumber}")
    public ResponseEntity<List<PurchaseRequisitionDTO>> getByBatchNumber(@PathVariable String batchNumber) {
        List<PurchaseRequisition> requisitions = service.getByBatchNumber(batchNumber);
        List<PurchaseRequisitionDTO> dtoList = requisitions.stream()
                .map(PurchaseRequisitionDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    // Helper methods for export (simplified placeholders)
    private byte[] generateCsvFile(List<PurchaseRequisitionDTO> requisitions) {
        // Implement CSV generation logic
        return "Sr.No,Part Number,Description,Current Stock,Required Qty,Required Date,Remark".getBytes();
    }

    private byte[] generateExcelFile(List<PurchaseRequisitionDTO> requisitions) {
        // Implement Excel generation logic using Apache POI
        return new byte[0];
    }

    private byte[] generatePdfFile(List<PurchaseRequisitionDTO> requisitions) {
        // Implement PDF generation logic using iText or similar library
        return new byte[0];
    }

    private byte[] generateTextFile(List<PurchaseRequisitionDTO> requisitions) {
        // Implement Text file generation logic
        return "Purchase Requisition Details".getBytes();
    }
}