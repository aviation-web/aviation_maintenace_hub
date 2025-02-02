package com.aeromaintenance.storeAcceptance;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "STORE_ACCEPTANCE")
public class StoreAcc {
	
	@Id
	@Column(name = "PART_NUM" ,nullable = false, length = 10)
	private String partNum;
	
	@Column(name = "PART_DESC" ,nullable = true, length = 100)
	private String description;
	
	@Column(name = "BATCH" ,nullable = false, length = 20)
	private int batch;
	
//	@Column(name = "CONDITION" ,nullable = false, length = 50)
//	private String condition;
	
	@Column(name = "NEW_OPT" ,nullable = true, length = 1)
	private boolean new_opt;
	
	@Column(name = "O_W" ,nullable = true, length = 1)
	private boolean o_w;
	
	@Column(name = "REPAIRED" ,nullable = true, length = 1)
	private boolean repaired;
	
	@Column(name = "SUPPLIER" ,nullable = false, length = 100)
	private String supplier;
	
	@Column(name = "DOM" ,nullable = false, length = 30)
	private Date dom;
	
	@Column(name = "DOE" ,nullable = false, length = 30)
	private Date doe;
	
	@Column(name = "QUANTITY" ,nullable = false, length = 7)
	private int quantity;
	
	@Column(name = "REVEIVING_INSP_REPORT" ,nullable = true, length = 500)
	private String recInspReport;
	
	@Column(name = "DATE_OF_RECEIPT" ,nullable = false, length = 30)
	private Date dateOfRecipet;
	
	@Column(name = "NAME_QUAILITY_INSP" ,nullable = false, length = 100)
	private String nameOfQualityInsp;
	
	@Column(name = "SIGNATURE_QUALITY_INSP" ,nullable = false, length = 20)
	private String signatureOfQualityInsp;
	
	@Column(name = "DATE_REGISTRATION" ,nullable = true, length = 30)
	private Date date;
	
	@Column(name = "FROM_AMC" ,nullable = true, length = 100)
	private String fromAMC;
	
	@Column(name = "REV_NO" ,nullable = true, length = 100)
	private String revNo;

}
