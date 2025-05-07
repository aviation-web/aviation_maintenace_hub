package com.aeromaintenance.inspectionReport;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartDetailsDTO {
	public PartDetailsDTO(String partNumbers) {
		this.partNumber = partNumbers;
	}
	private String partNumber;
	private String partDesc;
	private String purchaseOrderNo;
	private String supplierName;
	private String reportNo;
	private LocalDate date;
	private Integer qty;
	public PartDetailsDTO(String mrnNo, String supplierName, String orderNumber,
            LocalDate receiptDate, String partDescription, Integer quantity) {
			this.reportNo = mrnNo;
			this.supplierName = supplierName;
			this.purchaseOrderNo = orderNumber;
			this.date = receiptDate;
			this.partDesc = partDescription;
			this.qty = quantity;
}

}
