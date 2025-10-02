package com.aeromaintenance.customerOrder;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderDto {
	private String srNo;          // Changed from Long → String
	private Long orderNo;
	private Long batchNo;
	private Long roNo;
	private String roReceiveDate;
	private String customerName;
	private String partNo;
	private String partDescription;
	private Integer quantity;
	private String status;
	private String documentPath;
	private String makerUserName;
	private LocalDate makerDate;
	private String userRole;
	private String userAction;
	private String checkerUserName;
	private LocalDate checkerDate;
	private String remark;

	// ✅ Convert DTO to Entity
	public CustomerOrder toEntity() {
		CustomerOrder entity = new CustomerOrder();
		entity.setSrNo(this.srNo);
		entity.setOrderNo(this.orderNo);
		entity.setBatchNo(this.batchNo);
		entity.setRoNo(this.roNo);
		entity.setRoReceiveDate(this.roReceiveDate);
		entity.setCustomerName(this.customerName);
		entity.setPartNo(this.partNo);
		entity.setPartDescription(this.partDescription);
		entity.setQuantity(this.quantity);
		entity.setStatus(this.status);
		entity.setDocumentPath(this.documentPath);
		entity.setMakerUserName(this.makerUserName);
		entity.setMakerDate(this.makerDate);
		entity.setUserRole(this.userRole);
		entity.setUserAction(this.userAction);
		entity.setCheckerUserName(this.checkerUserName);
		entity.setCheckerDate(this.checkerDate);
		entity.setRemark(this.remark);
		return entity;
	}

	// ✅ Convert Entity to DTO
	public static CustomerOrderDto fromEntity(CustomerOrder entity) {
		return CustomerOrderDto.builder()
				.srNo(entity.getSrNo())
				.orderNo(entity.getOrderNo())
				.batchNo(entity.getBatchNo())
				.roNo(entity.getRoNo())
				.roReceiveDate(entity.getRoReceiveDate())
				.customerName(entity.getCustomerName())
				.partNo(entity.getPartNo())
				.partDescription(entity.getPartDescription())
				.quantity(entity.getQuantity())
				.status(entity.getStatus())
				.documentPath(entity.getDocumentPath())
				.makerUserName(entity.getMakerUserName())
				.makerDate(entity.getMakerDate())
				.userRole(entity.getUserRole())
				.userAction(entity.getUserAction())
				.checkerUserName(entity.getCheckerUserName())
				.checkerDate(entity.getCheckerDate())
				.remark(entity.getRemark())
				.build();
	}
}
