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
	public PartDetailsDTO(String mrnNo) {
		this.reportNo = mrnNo;
	}
	
	public PartDetailsDTO(String partNumbers,String partDescription) {
		this.partNumber = partNumbers;
		this.partDesc = partDescription;
	}
	private String partNumber;
	private String partDesc;
	private String purchaseOrderNo;
	private String supplierName;
	private String reportNo;
	private LocalDate date;
	private Integer qty;
	private Integer qtyReceive;
	
	public PartDetailsDTO(String mrnNo, String partNumber, String supplierName, String orderNumber,
            LocalDate receiptDate, String partDescription, Integer quantity, String qualityAcceptance) {
			this.reportNo = mrnNo;
			this.partNumber = partNumber;
			this.supplierName = supplierName;
			this.purchaseOrderNo = orderNumber;
			this.date = receiptDate;
			this.partDesc = partDescription;
			this.qty = quantity;
			this.qtyReceive = Integer.valueOf(qualityAcceptance);
}
	
	public PartDetailsDTO(String mrnNo, String supplierName, String orderNumber,
            LocalDate receiptDate, String partDescription, Integer quantity, String qualityAcceptance) {
			this.reportNo = mrnNo;
			this.supplierName = supplierName;
			this.purchaseOrderNo = orderNumber;
			this.date = receiptDate;
			this.partDesc = partDescription;
			this.qty = quantity;
			this.qtyReceive = Integer.valueOf(qualityAcceptance);
}

}
