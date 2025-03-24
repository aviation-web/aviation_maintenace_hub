package com.aeromaintenance.DispatchReport;



import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DispatchReportDTO {
    private Long id;
    private String reportNo;
    private LocalDate date;
    private String partNo;
    private String partDescription;
    private String orderNo;
    private String customerName;
    private int quantity;
    private String batchNo;
    private String challanNo;
    private String invoiceNo;
    private String caForm;
    private String ewayBill;
    private String storesInChargeName;
    private String storesInChargeSign;
}
