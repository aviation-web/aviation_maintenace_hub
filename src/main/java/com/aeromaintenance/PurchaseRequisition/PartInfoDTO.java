package com.aeromaintenance.PurchaseRequisition;

public class PartInfoDTO {
    private String partNumber;
    private String description;
    private String alternateProduct;
    private String unitOfMeasurement;

    public PartInfoDTO(String partNumber, String description, String alternateProduct, String unitOfMeasurement) {
        this.partNumber = partNumber;
        this.description = description;
        this.alternateProduct = alternateProduct;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getAlternateProduct() {
        return alternateProduct;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }
}
