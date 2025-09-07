package com.aeromaintenance.store.inventory;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;

public interface StoreInventoryProjection {
	
	@Value("#{target.id}")
    Long getId();

    @Value("#{target.part_num}")
    String getPartNum();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.dom}")
    Date getDom();

    @Value("#{target.doe}")
    Date getDoe();

    @Value("#{target.date_of_receipt}")
    Date getDateOfRecipet();

    @Value("#{target.quantity}")
    int getQuantity();

    
    @Value("#{target.location}")
    String getLocation();

}
