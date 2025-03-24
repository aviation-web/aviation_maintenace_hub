package com.aeromaintenance.PurchaseRequisition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_requisitions")
public class PurchaseRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sr_no", nullable = false, unique = true)
    private Integer srNo;

    @Column(name = "part_number", nullable = false)
    private String partNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "current_stock")
    private Integer currentStock;

    @Column(name = "required_qty")
    private Integer requiredQty;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
