package com.aeromaintenance.MaterialRequisition;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequisitionDTO {
    
    private Long id;
    private Long materialRequisitionNo;
    private Long workOrderNo;
    private Date date;
    private Long partNumber;
    private String description;
    private Integer requestedQty;
    private Integer issuedQty;
    private String batchLotNo;
    private String unitOfMeasurement;
}

