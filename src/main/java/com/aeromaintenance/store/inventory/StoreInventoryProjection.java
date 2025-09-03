package com.aeromaintenance.store.inventory;

import org.springframework.beans.factory.annotation.Value;

public interface StoreInventoryProjection {
	
	@Value("#{target.product_id}")
    Long getProductId();

    @Value("#{target.cmm_reference_number}")
    Integer getCmmReferenceNumber();

    @Value("#{target.material_classification}")
    String getMaterialClassification();

    @Value("#{target.nha}")
    String getNha();

    @Value("#{target.oem}")
    String getOem();

    @Value("#{target.product_description}")
    String getProductDescription();

    @Value("#{target.product_name}")
    String getProductName();

    @Value("#{target.registered_by}")
    String getRegisteredBy();

    @Value("#{target.registration_date}")
    java.sql.Date getRegistrationDate();

    @Value("#{target.unit_of_measurement}")
    String getUnitOfMeasurement();

    // Extra column from stored proc
    @Value("#{target.location}")
    String getLocation();

}
