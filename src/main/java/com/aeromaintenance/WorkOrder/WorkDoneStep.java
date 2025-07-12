package com.aeromaintenance.WorkOrder;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkDoneStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stepNumber;
    private String description;
    private String technicianSignDate;
    private String certifyingStaffSignStamp;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    @JsonBackReference
    private WorkOrder workOrder;
}
