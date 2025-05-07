package com.aeromaintenance.inspectionReport;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionReportDto {

	    private Long inspectionReportId;
		
		private String partNumber;
		
		private String partDesc;

		private String purchaseOrderNo;
		
		private String supplierName;
		
		private String reportNo;
		
		private LocalDate date;
		
		private Integer qty;
		
		private String invoiceObservation;
		
		private String manufacturerCertObservation;
		
		private String supplierCertObservation;	
		
		private String fullTraceabilityObservation;
		
		private String batchNumberObservation;
		
		private String dateOfManufacturingObservation;
		
		private String selfLifeObservation;
		
		private String tdsObservation;
		
		private String materialConditionObservation;
		
		private String specificationObservation;
		
		private String documentObservation;
		
		private String lotAccepted;
		
		private String remark;
		
		private String makerUserName;
		
		private String makerUserId;
		
		private LocalDate makerDate;
		
		private String checkerUserName;
		
		private String checkerUserId;
		
		private LocalDate checkerDate;
		
		private String userAction;
		
		private String userRole;

	}

