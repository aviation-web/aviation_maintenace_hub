package com.aeromaintenance.storeAcceptance;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreAccDto {
	private String partNum;
	private String description;
	private int batch;
//	private String condition;
	private boolean new_opt;
	private boolean o_w;
	private boolean repaired;
	private String supplier;
	private Date dom;
	private Date doe;
	private int quantity;
	private String recInspReport;
	private Date dateOfRecipet;
	private String nameOfQualityInsp;
	private String signatureOfQualityInsp;
	private Date date;
	private String fromAMC;
	private String revNo;
}
