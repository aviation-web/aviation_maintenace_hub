package com.aeromaintenance.DispatchReport;


import lombok.*;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispatch_reports")
public class DispatchReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String challanDate;
    private String challanRemark;
    private String invoiceNo;
    private String invoiceDate;
    private String invoiceRemark;
    private String caForm;
    private String caFormDate;
    private String caFormRemark;
    private String ewayBill;
    private String ewayBillDate;
    private String ewayBillRemark;

    private String storesInChargeName;
    private String storesInChargeSign;
}
