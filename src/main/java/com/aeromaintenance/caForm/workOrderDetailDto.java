package com.aeromaintenance.caForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class workOrderDetailDto {
	
	private String workOrderNumber;
	private String item;
	private String description;
	private String partNo;
	private Integer quantity;
	private String serialNo;
	private String customerName;
	private String repairOrderNo;
	private String formTrackingNumber;
	private String status;
	private String remark;
	

	public workOrderDetailDto(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public workOrderDetailDto(String description, String partNo, Integer quantity, String serialNo, String customerName,String repairOrderNo) {
		
		this.description = description;
		this.partNo = partNo;
		this.quantity = quantity;
		this.serialNo = serialNo;
		this.customerName = customerName;
		this.repairOrderNo = repairOrderNo;
	}
	
public workOrderDetailDto(String customerName,String repairOrderNo) {
		this.customerName = customerName;
		this.repairOrderNo = repairOrderNo;
	}
	
public workOrderDetailDto(String workOrderNo, String customerName, String repairOrderNo, String CAFormNo, String description, 
		String partNo, String item, Integer quantity, String serialNo, String status, String remark) {
		this.workOrderNumber = workOrderNo;
		this.customerName = customerName;
		this.repairOrderNo = repairOrderNo;
		this.formTrackingNumber = CAFormNo;
		this.description = description;
		this.partNo = partNo;
		this.item = item;
		this.quantity = quantity;
		this.serialNo = serialNo;
		this.customerName = customerName;
		this.repairOrderNo = repairOrderNo;
		this.status = status;
		this.remark = remark;
	}

public workOrderDetailDto(String workOrderNo, String description, String partNo, Integer quantity, String serialNo, String customerName,String repairOrderNo, String status) {
	this.workOrderNumber = workOrderNo;
	this.description = description;
	this.partNo = partNo;
	this.quantity = quantity;
	this.serialNo = serialNo;
	this.customerName = customerName;
	this.repairOrderNo = repairOrderNo;
	this.status = status;	
}

}
