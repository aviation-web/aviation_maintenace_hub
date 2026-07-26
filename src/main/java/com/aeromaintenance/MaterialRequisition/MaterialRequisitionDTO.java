package com.aeromaintenance.MaterialRequisition;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequisitionDTO {
    
    private Long id;
    private String materialRequisitionNo;
    private String workOrderNo;
    private Date date;
    private String partNumber;
    private String description;
    private Integer requestedQty;
//    private Integer issueQty;
    private Integer issuedQty;
//    private String batchLotNo;
    private String unitOfMeasurement;
    private String supplierName;
    private Date curDate;
    private String status;
    private int Rflag = 1;
}

