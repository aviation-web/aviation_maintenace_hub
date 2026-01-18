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
        private String dateFrom;
        private String dateTo;
        private Integer flag; // For active/inactive filtering
        // Add more filter criteria as needed
    }
}