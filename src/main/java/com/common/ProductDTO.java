package com.common;

public class ProductDTO {
    private String productName;
    private String productDescription;
    private String alternateProduct;
    private String unitOfMeasurement;

    public ProductDTO(String productName, String productDescription, String alternateProduct, String unitOfMeasurement) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.alternateProduct = alternateProduct;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getAlternateProduct() {
        return alternateProduct;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }
}
