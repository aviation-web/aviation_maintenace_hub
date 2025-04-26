package com.aeromaintenance.PurchaseOrder;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PurchaseOrderDTO {

    private String poNumber;
    private LocalDate poDate;
    private String ourReference;
    private String yourReference;
    private String delivery;
    private String deliveryAddress;
    private String paymentTerms;

    // Line Item Fields
    private String partNumber;
    private String description;
    private Integer currentStoke;
    private String batchNumber;
    private Integer srNo;
    private String unit;
    private Double ratePerUnit;
    private Double grossAmount;

    // Taxes & Totals
    private Double sgst;
    private Double cgst;
    private Double igst;
    private Double total;
    private Double grandTotal;
    private Double PF;
    private Double Transportation;
    private Double Other_Charges;
    private Double Insurance;

    // Other Info
    private String termsAndConditions;
    private String incoterm;
    private String currency;
    private String forwarder;

    // Getters and Setters
}

