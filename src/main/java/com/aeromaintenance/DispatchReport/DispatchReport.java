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
    private String invoiceNo;
    private String caForm;
    private String ewayBill;

    private String storesInChargeName;
    private String storesInChargeSign;
}
