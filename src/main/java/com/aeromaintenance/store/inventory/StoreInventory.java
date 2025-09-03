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
	
	private Long productId;

    private String productName;

    private String productDescription;

    private String unitOfMeasurement;

    private String oem;

    private String nha;

    private int cmmReferenceNumber;

    private String materialClassification;

    private Date registrationDate = new Date();

    private String registeredBy;

    private String location;

}
