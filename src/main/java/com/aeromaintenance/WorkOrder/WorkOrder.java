package com.aeromaintenance.WorkOrder;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "WorkOrder")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workOrderNo;
    private String repairOrder;
    private String customerName;
    private String partNumber;
    private String description;
    private Integer quantity;
    private String cmmRefNo;
    private String serialNumber;
    private String revisionNo;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(length = 500)
    private String workshopManagerRemarks;

    private String issuedBy;
    private String tools;
    private Integer manHours;
    private String certifyingStaff;
    private String technician;
    private Integer totalManhour;

    @Transient
    private Long customerOrderSrNo;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WorkDoneStep> workDoneSteps = new ArrayList<>();

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PartsUsed> partsUsed = new ArrayList<>();
}
