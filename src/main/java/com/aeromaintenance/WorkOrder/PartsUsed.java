package com.aeromaintenance.WorkOrder;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Entity
public class PartsUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String partNo;
    private String serialOrBatchNo;
    private Integer quantity;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    @JsonBackReference
    private WorkOrder workOrder;
}
