package com.aeromaintenance.PurchaseOrder;

import lombok.Data;

@Data
public class PurchaseOrderItem {
    private String batchNumber;
    private Long id;
    private String partNumber;
    private String description;
    private Integer requiredQty;
    private String units;
    private Double rate;
    private Double gross;
}
