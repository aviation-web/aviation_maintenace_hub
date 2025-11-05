package com.aeromaintenance.PurchaseOrder;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseOrderDTO {
	
	private Long id;
    private String poNumber;
    private LocalDate poDate;
    private String ourReference;
    private String yourReference;
    private String delivery;
    private String deliveryAddress;
    private String paymentTerms;
    private String status;
    private String Address;

    // Line Item Fields
    private String partNumber;
    private String description;
    private Integer currentStoke;
    private String batchNumber;
    private Integer srNo;
    private String unit;
    private Double ratePerUnit;
    private Double grossAmount;
    private String SupplierName;
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
    private Double cgstPercentage;
    private Double igstPercentage;
    private Double sgstPercentage;

    // Other Info
    private String termsAndConditions;
    private String incoterm;
    private String currency;
    private String forwarder;

    private List<PurchaseOrderItem> items;
    // Getters and Setter
    
    public PurchaseOrderDTO() { }
    
    public PurchaseOrderDTO(String description, Integer currentStoke, String unit, LocalDate poDate) {
        this.description = description;
        this.currentStoke = currentStoke;
        this.unit = unit;
        this.poDate = poDate;
    }
    
    public PurchaseOrderDTO(String poNumber) {
        this.poNumber = poNumber;
        
    }
    
    public PurchaseOrderDTO(String partNumber,Long id, String SupplierName) {
        this.partNumber = partNumber;
        this.id = id;
        this.SupplierName= SupplierName;
        
    }
}

