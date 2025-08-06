package com.aeromaintenance.WorkOrder;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data

public class WorkOrderStep {



    @Id
    private Integer stepNo;
    
    @Column(columnDefinition = "TEXT")
    private String detailOfWorkDone;
    
    private String technicianSign;
    
    private String certifyingStaffSign;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "work_order_no")
    private WorkOrder workOrder;
    // Constructors
    public WorkOrderStep() {}

    public WorkOrderStep(Integer stepNo, String detailOfWorkDone, String technicianSign,
                         String certifyingStaffSign, WorkOrder workOrder) {
        this.stepNo = stepNo;
        this.detailOfWorkDone = detailOfWorkDone;
        this.technicianSign = technicianSign;
        this.certifyingStaffSign = certifyingStaffSign;
        this.workOrder = workOrder;
    }

    // Getters and Setters

    public Integer getStepNo() {
        return stepNo;
    }

    public void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    public String getDetailOfWorkDone() {
        return detailOfWorkDone;
    }

    public void setDetailOfWorkDone(String detailOfWorkDone) {
        this.detailOfWorkDone = detailOfWorkDone;
    }

    public String getTechnicianSign() {
        return technicianSign;
    }

    public void setTechnicianSign(String technicianSign) {
        this.technicianSign = technicianSign;
    }

    public String getCertifyingStaffSign() {
        return certifyingStaffSign;
    }

    public void setCertifyingStaffSign(String certifyingStaffSign) {
        this.certifyingStaffSign = certifyingStaffSign;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }
}
