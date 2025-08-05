package com.aeromaintenance.caForm;

import java.time.LocalDate;

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
@Table(name = "ca_form")
public class CAForm {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String formTrackingNumber;
	private String workOrderNo;
	private String item;
	private String description;
	private String partNo;
	private Integer quantity;
	private String serialNo;
	private String status;
	private String remarks;
	private String approveDesign13a;
	private String nonApproveDesign13a;
	private String otherRegulation14a;
	private String authorisedSign13b;
	private String authorisationNumber13c;
	private String authorisedSign14b;
	private String approvalRefNo14c;
	private String name13d;
	private String date13e;
	private String name14d;
	private String date14e;

}
