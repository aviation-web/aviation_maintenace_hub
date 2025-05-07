package com.aeromaintenance.WorkOrder;



import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MaterialRequisitionOrder {
    private String description;
    private String partNo;
    private String serialOrBatchNo;
    private int qty;
    private String remarks;

    // Getters and Setters
}
