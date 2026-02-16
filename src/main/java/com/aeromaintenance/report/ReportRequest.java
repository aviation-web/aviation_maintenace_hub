package com.aeromaintenance.report;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportRequest {
    private String entityName; // "Product", "Inventory", "WorkOrder", etc.
    private List<String> columns; // ["productName", "productDescription", "unitOfMeasurement"]
    private ReportFilter filter; // Optional filters
    private String format; // "CSV", "EXCEL", "JSON"

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportFilter {
        private String dateFrom;  // Format: "yyyy-MM-dd"
        private String dateTo;    // Format: "yyyy-MM-dd"
        private Integer flag;     // For active/inactive filtering
        private String dateField; // Which date field to filter on (e.g., "registrationDate", "createdDate")
        // Add more filter criteria as needed
    }
}