package com.aeromaintenance.inspectionReport;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "Inspection_Report")
public class InspectionReport {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long inspectionReportId;
	
	@Column
	private String partNumber;
	
	@Column
	private String partDesc;
	
	@Column
	private String purchaseOrderNo;
	
	@Column
	private String supplierName;
	
	@Column
	private String reportNo;
	
	@Column
	private LocalDate date;
	
	@Column
	private Integer qty;
	
	@Column
	private String invoiceObservation;
	
	@Column
	private String manufacturerCertObservation;
	
	@Column
	private String supplierCertObservation;	
	
	@Column
	private String fullTraceabilityObservation;
	
	@Column
	private String batchNumberObservation;
	
	@Column
	private String dateOfManufacturingObservation;
	
	@Column
	private String selfLifeObservation;
	
	@Column
	private String tdsObservation;
	
	@Column
	private String materialConditionObservation;
	
	@Column
	private String specificationObservation;
	
	@Column
	private String documentObservation;
	
	@Column
	private String lotAccepted;
	
	@Column
	private String remark;
	
	@Column
	private String makerUserName;
	
	@Column
	private String makerUserId;
	
	@Column
	private LocalDate makerDate;
	
	@Column
	private String checkerUserName;
	
	@Column
	private String checkerUserId;
	
	@Column
	private LocalDate checkerDate;
	
	@Column
	private String userAction;
	
	@Column
	private String userRole;

}
