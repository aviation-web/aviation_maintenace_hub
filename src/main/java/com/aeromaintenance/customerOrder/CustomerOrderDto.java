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
	  private String  srNo;
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
	  
	// Convert DTO to Entity
	    public CustomerOrder toEntity() {
	    	CustomerOrder entity = new CustomerOrder();
	        entity.setSrNo(this.srNo);
	        entity.setPartNo(this.partNo);
	        entity.setPartDescription(this.partDescription);
	        entity.setRoNo(this.roNo);
	        entity.setRoReceiveDate(this.roReceiveDate);
	        entity.setCustomerName(this.customerName);
	        entity.setBatchNo(this.batchNo);
	        entity.setQuantity(this.quantity);
	        entity.setStatus(this.status);
	        return entity;
	    }

	    // Convert Entity to DTO
	    public static CustomerOrderDto fromEntity(CustomerOrder entity) {
	        return CustomerOrderDto.builder()
	                .srNo(entity.getSrNo())
	                .partNo(entity.getPartNo())
	                .partDescription(entity.getPartDescription())
	                .roNo(entity.getRoNo())
	                .roReceiveDate(entity.getRoReceiveDate())
	                .customerName(entity.getCustomerName())
	                .batchNo(entity.getBatchNo())
	                .quantity(entity.getQuantity())
	                .status(entity.getStatus())
	                .build();
	    }
	    

}
