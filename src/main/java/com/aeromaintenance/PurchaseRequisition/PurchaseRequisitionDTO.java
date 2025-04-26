package com.aeromaintenance.PurchaseRequisition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequisitionDTO {

    private Long id;
    private Integer srNo;
    private String partNumber;
    private String description;
    private Integer currentStock;
    private Integer requiredQty;
    private LocalDate requiredDate;
    private String remark;
    private String batchnumber;
    private String prflag;

    // Convert DTO to Entity
    public PurchaseRequisition toEntity() {
        PurchaseRequisition entity = new PurchaseRequisition();
        entity.setId(this.id);
        entity.setSrNo(this.srNo);
        entity.setPartNumber(this.partNumber);
        entity.setDescription(this.description);
        entity.setCurrentStock(this.currentStock);
        entity.setRequiredQty(this.requiredQty);
        entity.setRequiredDate(this.requiredDate);
        entity.setRemark(this.remark);
        entity.setBatchNumber(this.batchnumber);
        entity.setPrflag(this.prflag);
        return entity;
    }

    // Convert Entity to DTO
    public static PurchaseRequisitionDTO fromEntity(PurchaseRequisition entity) {
        return PurchaseRequisitionDTO.builder()
                .id(entity.getId())
                .srNo(entity.getSrNo())
                .partNumber(entity.getPartNumber())
                .description(entity.getDescription())
                .currentStock(entity.getCurrentStock())
                .requiredQty(entity.getRequiredQty())
                .requiredDate(entity.getRequiredDate())
                .remark(entity.getRemark())
                .batchnumber(entity.getBatchNumber())
                .prflag(entity.getPrflag())
                .build();
    }
    
    public PurchaseRequisitionDTO(Long id, String partNumber, String description) {
        this.id = id;
        this.partNumber = partNumber;
        this.description = description;
    }
}
