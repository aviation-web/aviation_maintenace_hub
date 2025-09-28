package com.aeromaintenance.store.inventory;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;

public interface StoreInventoryProjection {

    @Value("#{target.inventory_id}")
    Long getId();

    @Value("#{target.part_number}")
    String getPartNum();

    @Value("#{target.part_description}")
    String getDescription();

    @Value("#{target.quantity}")
    int getQuantity();

    @Value("#{target.rack_no}")
    String getRackNo();

    @Value("#{target.updated_by}")
    String getUpdatedBy();

    @Value("#{target.updated_date}")
    java.sql.Timestamp getUpdatedDate();

    @Value("#{target.location}")
    String getLocation();
}
