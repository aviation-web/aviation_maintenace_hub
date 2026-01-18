package com.aeromaintenance.PurchaseRequisition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PurchaseRequisitionDTO {

    private Long id;
    private String partNumber;
    private String description;
    private Integer currentStock;
    private Integer requiredQty;
    private String unitOfMeasurement;
    private LocalDate requiredDate;
    private String remark;
    private String batchNumber;
    private String status;
    private int Rflag;

    // Convert DTO to Entity
    public PurchaseRequisition toEntity() {
        PurchaseRequisition entity = new PurchaseRequisition();
        entity.setId(this.id);
        entity.setPartNumber(this.partNumber);
        entity.setDescription(this.description);
        entity.setCurrentStock(this.currentStock);
        entity.setRequiredQty(this.requiredQty);
        entity.setUnitOfMeasurement(this.unitOfMeasurement);
        entity.setRequiredDate(this.requiredDate);
        entity.setRemark(this.remark);
        entity.setBatchNumber(this.batchNumber);
        entity.setStatus(this.status);
        entity.setRflag(this.Rflag);
        return entity;
    }

    // Convert Entity to DTO
    public static PurchaseRequisitionDTO fromEntity(PurchaseRequisition entity) {
        return PurchaseRequisitionDTO.builder()
                .id(entity.getId())
                .partNumber(entity.getPartNumber())
                .description(entity.getDescription())
                .currentStock(entity.getCurrentStock())
                .requiredQty(entity.getRequiredQty())
                .unitOfMeasurement(entity.getUnitOfMeasurement())
                .requiredDate(entity.getRequiredDate())
                .remark(entity.getRemark())
                .batchNumber(entity.getBatchNumber())
                .status(entity.getStatus())
                .Rflag(entity.getRflag())
                .build();
    }
    
    public PurchaseRequisitionDTO(Long id, String partNumber, String description) {
        this.id = id;
        this.partNumber = partNumber;
        this.description = description;
    }
}
