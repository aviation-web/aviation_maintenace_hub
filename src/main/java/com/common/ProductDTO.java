package com.common;

public class ProductDTO {
    private String productName;
    private String productDescription;

    public ProductDTO(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
