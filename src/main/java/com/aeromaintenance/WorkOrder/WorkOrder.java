package com.aeromaintenance.WorkOrder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    private String workOrderNo; // Primary Key

    private LocalDate issueDate;
    private String customerName;
    private String repairOrderNo;
    private String partNumber;
    private Integer qty;
    private String description;
    private String cmmRefNo;
    private String revNo;
    private LocalDate cmmRevDate;
    private String workshopManagerRemarks;
    private String issuedBy;
    private Integer certifyingStaffhours;
    private String technician;
    private String totalManHour;
    private String actionTaken;
    private String toolsUsed;
    private LocalDate qualityManagerSignDate;
    private LocalDate workshopManagerSignDate;
    private String snBn;
    private String srNumber; // customer_order sr_no

    //  NEW FIELD
    private String status; // e.g. "OPEN", "IN-PROGRESS", "COMPLETED"

    @JsonManagedReference
    @OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkOrderStep> workOrderSteps = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "workOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialRequisitionNew> materialRequisitions = new ArrayList<>();
}
