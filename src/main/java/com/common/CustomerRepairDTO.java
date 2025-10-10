package com.common;

import java.util.Map;

public class CustomerRepairDTO {
    private String productName;
    private String productDescription;
    private String unitOfMeasurement;
    private Map<String, String> productSerialNumbers; // changed from List to Map

    public CustomerRepairDTO(String productName, String productDescription, String unitOfMeasurement,
                             Map<String, String> productSerialNumbers) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.unitOfMeasurement = unitOfMeasurement;
        this.productSerialNumbers = productSerialNumbers;
    }

    // getters
    public String getProductName() { return productName; }
    public String getProductDescription() { return productDescription; }
    public String getUnitOfMeasurement() { return unitOfMeasurement; }
    public Map<String, String> getProductSerialNumbers() { return productSerialNumbers; }

    // setters (optional if needed)
    public void setProductName(String productName) { this.productName = productName; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public void setUnitOfMeasurement(String unitOfMeasurement) { this.unitOfMeasurement = unitOfMeasurement; }
    public void setProductSerialNumbers(Map<String, String> productSerialNumbers) { this.productSerialNumbers = productSerialNumbers; }
}
