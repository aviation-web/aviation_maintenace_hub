package com.aeromaintenance.WorkOrder;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WorkStep {
    private int srNo;
    private String detailOfWork;
    private String technicianSignDate;
    private String certifyingSignStamp;

    // Getters and Setters
}
