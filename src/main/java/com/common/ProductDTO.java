package com.common;

public class ProductDTO {
    private String productName;
    private String productDescription;
    private String alternateProduct1;
    private String alternateProduct2;
    private String unitOfMeasurement;
    private String mappingType;

    public ProductDTO(String productName, String productDescription, String alternateProduct1, String alternateProduct2, String unitOfMeasurement, String mappingType) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.alternateProduct1 = alternateProduct1;
        this.alternateProduct2 = alternateProduct2;
        this.unitOfMeasurement = unitOfMeasurement;
        this.mappingType = mappingType;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getAlternateProduct1() {
        return alternateProduct1;
    }

    public String getAlternateProduct2() {
        return alternateProduct2;
    }

    public String getMappingType() {
        return mappingType;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }
}
