package com.aeromaintenance.MaterialReceiptNote;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "material_receipt_note")
public class MaterialReceiptNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long materialId;
    
    @Column(name = "mrn_no", length = 15, nullable = false)
    private String mrnNo;
    
    @Column(name = "supplier_name", length = 100, nullable = false)
    private String supplierName;
    
    @Column(name = "order_number", length = 20, nullable = false)
    private String orderNumber;
    
    @Column(name = "challan_no", length = 50, nullable = false)
    private String challanNo;
    
    @Column(name = "receipt_date", nullable = false)
    private LocalDate receiptDate;
    
    @Column(name = "part_number", length = 20, nullable = false)
    private String partNumber;
    
    @Column(name = "part_description", length = 200, nullable = false)
    private String partDescription;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_of_measurement", length = 20, nullable = false)
    private String unitOfMeasurement;


    @Column(name = "store_incharge_sign")
    private String storeInchargeSign;
    
    @Column(name = "quality_acceptance")
    private String qualityAcceptance;
    
    @Column(name = "status", nullable = false)
    private String status = "Open";

    @Column
    private int Rflag = 1;

}
