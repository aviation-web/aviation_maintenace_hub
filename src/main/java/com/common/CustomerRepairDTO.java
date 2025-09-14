package com.common;

public class CustomerRepairDTO {
        private String productName;
        private String productDescription;
        private String unitOfMeasurement;

    public CustomerRepairDTO(String productName, String productDescription, String unitOfMeasurement) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }
}
