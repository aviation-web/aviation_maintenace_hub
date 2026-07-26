package com.aeromaintenance.PurchaseOrder;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "po_number")
    private String poNumber;

    @NotNull(message = "PO Date is required")
    private LocalDate poDate;

    @Column(name = "status", nullable = false)
    private String status = "Open";
    
    @Column(name = "orderPlacedFlag", nullable = false)
    private int orderPlacedFlag = 0;

    @Column(nullable = false)
    private int Rflag = 1;

    private String SupplierName;

    private String ourReference;
    private String yourReference;
    private String delivery;
    private String Address;
    
    @Column(name = "sr_no", unique = true)
    private Integer srNo;

    @NotBlank(message = "Delivery Address is required")
    private String deliveryAddress;

    private String paymentTerms;

    // --- Line Item Details ---
    private String partNumber;

    private String description;

    
    private Integer currentStoke;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Rate per unit is required")
    @NotNull( message = "Rate per unit must be positive")
    private Double ratePerUnit;

    @DecimalMin(value = "0.0", message = "Gross amount must be 0 or more")
    private Double grossAmount;

    // --- Tax and Charges ---
    private Double sgst;
    private Double cgst;
    private Double igst;
    private Double total;
    private Double grandTotal;
    private Double PF;
    private Double Transportation;
    private Double Other_Charges;
    private Double Insurance;
    private Double grossOfSingle;
    private Double cgstPercentage;
    private Double igstPercentage;
    private Double sgstPercentage;

    // --- Footer Info ---
    private String termsAndConditions;
    private String incoterm;
    private String currency;
    private String forwarder;
	

    // Getters and setters
}

