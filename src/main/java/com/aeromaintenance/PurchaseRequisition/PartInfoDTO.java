package com.aeromaintenance.PurchaseRequisition;

public class PartInfoDTO {
    private String partNumber;
    private String description;

    public PartInfoDTO(String partNumber, String description) {
        this.partNumber = partNumber;
        this.description = description;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getDescription() {
        return description;
    }
}
