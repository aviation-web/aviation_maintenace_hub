package com.aeromaintenance.MaterialRequisition;


import lombok.*;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "material_requisition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_requisition_no", unique = true, nullable = false)
    private String materialRequisitionNo;

    @Column(name = "workorder_no", nullable = false)
    private String workOrderNo;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "part_no", nullable = false)
    private String partNumber;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "requested_qty", nullable = false)
    private Integer requestedQty;

//    @Column(name= "issue_qty", nullable = false)
//    private Integer issueQty;

    @Column(name = "issued_qty", nullable = false)
    private Integer issuedQty;

//    @Column(name = "batch_lot_no")
//    private String batchLotNo;

    @Column(name = "unit_of_measurement", nullable = false)
    private String unitOfMeasurement;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Column(name = "cur_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date curDate;

    @Column(name = "status", nullable = false)
    private String status = "Open"; // 1 = active, 0 = submitted/hidden

    @Column
    private int Rflag = 1;
}

