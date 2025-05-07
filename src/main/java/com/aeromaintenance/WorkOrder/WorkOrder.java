package com.aeromaintenance.WorkOrder;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "WorkOrder")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String repairOrderNo;
    private String issueDate;
    private String operatorName;
    private String serialNumber;
    private String batchNumber;
    private int quantity;
    private String description;
    private String cmmRef;
    private String revNo;
    private String workshopManagerRemarks;

    // ElementCollection for WorkStep
    @ElementCollection
    @CollectionTable(
            name = "workorder_steps",
            joinColumns = @JoinColumn(name = "workorder_id")
    )
    private List<WorkStep> workSteps = new ArrayList<>();

    // ElementCollection for MaterialRequisitionOrder
    @ElementCollection
    @CollectionTable(
            name = "workorder_materials",
            joinColumns = @JoinColumn(name = "workorder_id")
    )
    private List<MaterialRequisitionOrder> materials = new ArrayList<>();

    private String certifyingStaff;
    private String certifyingDate;
    private int technicianHours;
    private int certifyingStaffHours;
    private int totalManHours;
    private boolean documentsVerified;
    private String finalApprovalByWorkshopManager;
    private String finalApprovalByQualityManager;
    private String finalDate;

    // Getters and Setters
}
