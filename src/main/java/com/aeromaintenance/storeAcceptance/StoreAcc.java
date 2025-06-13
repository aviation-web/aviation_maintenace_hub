package com.aeromaintenance.storeAcceptance;



import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;
@ToString
@Entity
@Table(name = "STORE_ACCEPTANCE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "part_num", nullable = false)
    private String partNum;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "batch")
    private String batch;

    @Column(name = "condition_type")
    private String condition; // "New", "O/W", "Repaired"

    @Column(name = "supplier", length = 100)
    private String supplier;

    @Temporal(TemporalType.DATE)
    @Column(name = "dom")
    private Date dom; // Date of Manufacture

    @Temporal(TemporalType.DATE)
    @Column(name = "doe")
    private Date doe; // Date of Expiry

    @Column(name = "quantity")
    private int quantity;

//    @Column(name = "inspection_report", length = 500)
//    private String document;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_receipt")
    private Date dateOfRecipet;

    @Column(name = "quality_inspector", length = 100)
    private String nameOfQualityInsp;

    @Column(name = "signature_of_quality_inspector", length = 20)
    private String signatureOfQualityInsp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "form_amc", updatable = false)
    private String formAMC;


    @Column(name = "rev_no", updatable = false)
    private String revNo;

    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.revNo = generateRevNo();  // Automatically generate rev_no
        this.formAMC = generateFormAMC();

    }

    private String generateRevNo() {
        return "REV-" + System.currentTimeMillis(); // Example: REV-1708607000000
    }
    private String generateFormAMC() {
        return "AMC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // Example: AMC-A1B2C3D4
    }
}
