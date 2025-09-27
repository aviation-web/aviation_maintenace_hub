package com.aeromaintenance.store.inventory;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreInventory {
	
	private Long id;
	
	private String partNum;

    private String description;

    private int quantity;

    private String location;

    private String rackNo;

    private String updatedBy;

    private String updatedDate;
}
