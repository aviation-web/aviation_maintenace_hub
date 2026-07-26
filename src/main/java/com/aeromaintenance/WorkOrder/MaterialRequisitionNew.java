package com.aeromaintenance.WorkOrder;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class MaterialRequisitionNew {



    @Id
    private Integer srNo;
    private String description;
    private String partNo;
    private String snbn;
    private Integer qty;
    private String remarks;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "work_order_no")
    private WorkOrder workOrder;

    // Constructors
    public MaterialRequisitionNew() {
    }

    public MaterialRequisitionNew(Integer srNo, String description, String partNo, String snbn, Integer qty, String remarks, WorkOrder workOrder) {
        this.srNo = srNo;
        this.description = description;
        this.partNo = partNo;
        this.snbn = snbn;
        this.qty = qty;
        this.remarks = remarks;
        this.workOrder = workOrder;
    }

    public Integer getSrNo() {
        return srNo;
    }

    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getSnbn() {
        return snbn;
    }

    public void setSnbn(String snbn) {
        this.snbn = snbn;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }
}
