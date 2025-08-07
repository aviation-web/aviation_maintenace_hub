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
    private LocalDate reportDate;
    private String partNo;
    private String partDescription;
    private String orderNo;
    private String customerName;
    private int quantity;
    private String batchNo;
    private String challanNo;
    private String challanDate;
    private String challanRemark;
    private String invoiceNo;
    private String invoiceDate;
    private String invoiceRemark;
    private String caFormNo;
    private String caFormDate;
    private String caFormRemark;
    private String ewayBill;
    private String ewayBillDate;
    private String ewayBillRemark;
    private String storesInChargeName;
    private String storesInChargeSign;
}
