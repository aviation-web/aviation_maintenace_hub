package com.common;

public class SupplierNamePaymentDTO {
    private String supplierName;
    private String paymentTerms;
    private String address;

    public SupplierNamePaymentDTO(String supplierName, String paymentTerms) {
        this.supplierName = supplierName;
        this.paymentTerms = paymentTerms;
    }
    
    public SupplierNamePaymentDTO(String supplierName, String paymentTerms, String address) {
        this.supplierName = supplierName;
        this.paymentTerms = paymentTerms;
        this.address = address;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }
    
    public String getAddress() {
    	return address;
    }
}
