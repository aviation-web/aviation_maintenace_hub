package com.aeromaintenance.product;


public enum MaterialClassification {
    NEW_PRODUCT,
    RAW_PRODUCT;

    @Override
    public String toString() {
        // Convert the enum constant to a user-friendly string if needed
        return this.name().replace("_", " ");
    }
}

