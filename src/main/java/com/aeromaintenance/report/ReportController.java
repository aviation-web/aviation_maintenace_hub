package com.aeromaintenance.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/reports")
//@CrossOrigin(origins = "*") // Configure as needed
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportExportService exportService;

    /**
     * Get all available components/entities (10-15 components)
     * Returns: ["Product", "Inventory", "WorkOrder", "PurchaseOrder", ...]
     */
    @GetMapping("/entities")
    public ResponseEntity<Set<String>> getAvailableEntities() {
        return ResponseEntity.ok(reportService.getAvailableEntities());
    }

    /**
     * Get ALL columns dynamically for a specific entity
     * Automatically discovers columns using reflection - NO MANUAL MAPPING!
     *
     * Example: /api/reports/entities/Product/columns
     * Returns: {
     *   "productId": "Product ID",
     *   "productName": "Product Name",
     *   "productDescription": "Product Description",
     *   ... all columns automatically
     * }
     */
    @GetMapping("/entities/{entityName}/columns")
    public ResponseEntity<?> getAvailableColumns(@PathVariable String entityName) {
        try {
            Map<String, String> columns = reportService.getAvailableColumns(entityName);
            return ResponseEntity.ok(columns);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get detailed information about an entity
     */
    @GetMapping("/entities/{entityName}/info")
    public ResponseEntity<?> getEntityInfo(@PathVariable String entityName) {
        try {
            Map<String, Object> info = reportService.getEntityInfo(entityName);
            return ResponseEntity.ok(info);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Generate report in JSON format (preview before download)
     * User selects:
     * - entityName: which component (Product, Inventory, etc.)
     * - columns: which specific columns they want (can select 1, 5, 10, or all)
     * - filter: optional filters
     */
    @PostMapping("/generate")
    public ResponseEntity<?> generateReport(@RequestBody ReportRequest request) {
        try {
            // Validate request
            if (request.getEntityName() == null || request.getEntityName().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Entity name is required"));
            }

            if (request.getColumns() == null || request.getColumns().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "At least one column must be selected"));
            }

            List<Map<String, Object>> reportData = reportService.generateReport(request);

            return ResponseEntity.ok(Map.of(
                    "entityName", request.getEntityName(),
                    "totalRecords", reportData.size(),
                    "columns", request.getColumns(),
                    "data", reportData
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate report: " + e.getMessage()));
        }
    }

    /**
     * Download report as CSV
     * User can select ANY columns from ANY entity dynamically
     */
    @PostMapping("/download/csv")
    public ResponseEntity<?> downloadReportAsCSV(@RequestBody ReportRequest request) {
        try {
            // Validate request
            if (request.getEntityName() == null || request.getColumns() == null || request.getColumns().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Entity name and columns are required"));
            }

            List<Map<String, Object>> reportData = reportService.generateReport(request);

            if (reportData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "No data found for the selected criteria"));
            }

            ByteArrayOutputStream outputStream = exportService.exportToCSV(reportData, request.getColumns());

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            String filename = request.getEntityName() + "_Report_" + System.currentTimeMillis() + ".csv";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate CSV: " + e.getMessage()));
        }
    }

    /**
     * Download report as Excel
     * User can select ANY columns from ANY entity dynamically
     */
    @PostMapping("/download/excel")
    public ResponseEntity<?> downloadReportAsExcel(@RequestBody ReportRequest request) {
        try {
            // Validate request
            if (request.getEntityName() == null || request.getColumns() == null || request.getColumns().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Entity name and columns are required"));
            }

            List<Map<String, Object>> reportData = reportService.generateReport(request);

            if (reportData.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(Map.of("message", "No data found for the selected criteria"));
            }

            ByteArrayOutputStream outputStream = exportService.exportToExcel(
                    reportData,
                    request.getColumns(),
                    request.getEntityName()
            );

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            String filename = request.getEntityName() + "_Report_" + System.currentTimeMillis() + ".xlsx";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to generate Excel: " + e.getMessage()));
        }
    }

    /**
     * Get sample data for preview (first 10 records)
     */
    @GetMapping("/entities/{entityName}/preview")
    public ResponseEntity<?> getPreviewData(@PathVariable String entityName) {
        try {
            Map<String, String> columns = reportService.getAvailableColumns(entityName);

            ReportRequest previewRequest = new ReportRequest();
            previewRequest.setEntityName(entityName);
            previewRequest.setColumns(new java.util.ArrayList<>(columns.keySet()));

            // Get first 10 records
            List<Map<String, Object>> data = reportService.generateReport(previewRequest);
            List<Map<String, Object>> preview = data.stream()
                    .limit(10)
                    .collect(java.util.stream.Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "columns", columns,
                    "preview", preview,
                    "totalColumns", columns.size()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}