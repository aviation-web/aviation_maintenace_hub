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
	
	private String workOrderNo;
	private String item;
	private String description;
	private String partNo;
	private Integer quantity;
	private String serialNo;

	public workOrderDetailDto(String workOrderNumber) {
		this.workOrderNo = workOrderNumber;
	}

	public workOrderDetailDto(String description, String partNo, Integer quantity, String serialNo) {
		
		this.description = description;
		this.partNo = partNo;
		this.quantity = quantity;
		this.serialNo = serialNo;
	}

}
