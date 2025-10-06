package com.common;

import java.util.List;

public class CustomerRepairDTO {
    private String productName;
    private String productDescription;
    private String unitOfMeasurement;
    private List<String> productSerialNumbers; // new field

    public CustomerRepairDTO(String productName, String productDescription, String unitOfMeasurement, List<String> productSerialNumbers) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.unitOfMeasurement = unitOfMeasurement;
        this.productSerialNumbers = productSerialNumbers;
    }

    // getters
    public String getProductName() { return productName; }
    public String getProductDescription() { return productDescription; }
    public String getUnitOfMeasurement() { return unitOfMeasurement; }
    public List<String> getProductSerialNumbers() { return productSerialNumbers; }
}
