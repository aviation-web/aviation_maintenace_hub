package com.common;

public class ProductDTO {
    private String productName;
    private String productDescription;
    private String alternateProduct1;
    private String alternateProduct2;
    private String unitOfMeasurement;
    private String mappingType;
    private int quantity;
    private int alternateQuantity1;
    private int alternateQuantity2;

    public ProductDTO(String productName, String productDescription, String alternateProduct1, String alternateProduct2, String unitOfMeasurement, String mappingType) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.alternateProduct1 = alternateProduct1;
        this.alternateProduct2 = alternateProduct2;
        this.unitOfMeasurement = unitOfMeasurement;
        this.mappingType = mappingType;
    }

    public ProductDTO(String productName, String productDescription, String alternateProduct1, String alternateProduct2, String unitOfMeasurement, String mappingType, int quantity, int alternateQuantity1, int alternateQuantity2) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.alternateProduct1 = alternateProduct1;
        this.alternateProduct2 = alternateProduct2;
        this.unitOfMeasurement = unitOfMeasurement;
        this.mappingType = mappingType;
        this.quantity = quantity;
        this.alternateQuantity1 = alternateQuantity1;
        this.alternateQuantity2 = alternateQuantity2;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAlternateQuantity1() {
        return alternateQuantity1;
    }

    public void setAlternateQuantity1(int alternateQuantity1) {
        this.alternateQuantity1 = alternateQuantity1;
    }

    public int getAlternateQuantity2() {
        return alternateQuantity2;
    }

    public void setAlternateQuantity2(int alternateQuantity2) {
        this.alternateQuantity2 = alternateQuantity2;
    }
}
