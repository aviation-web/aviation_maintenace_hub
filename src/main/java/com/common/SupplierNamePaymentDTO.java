package com.common;

public class SupplierNamePaymentDTO {
    private String supplierName;
    private String paymentTerms;

    public SupplierNamePaymentDTO(String supplierName, String paymentTerms) {
        this.supplierName = supplierName;
        this.paymentTerms = paymentTerms;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }
}
