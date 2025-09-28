package com.aeromaintenance.product;
import lombok.*;

import java.util.Date;

import javax.persistence.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_registration")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 255, unique = true)
    private String productName;

    @Column(nullable = true, length = 255)
    private String alternateProduct1;

    @Column(nullable = true, length = 255)
    private String alternateProduct2;

    @Column(name = "mapping_type", nullable = false, length = 10)
    private String mappingType; // UP / DOWN / BOTH

    @Column(nullable = false, length = 255)
    private String productDescription;

    @Column(nullable = false, length = 6)
    private String unitOfMeasurement;

    @Column(nullable = false, length = 255)
    private String oem;

    @Column(nullable = false, length = 255)
    private String nha;

    @Column(nullable = false)
    private int cmmReferenceNumber;

    @Column(nullable = false, length = 30)
    private String materialClassification;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate = new Date();

    @Column(nullable = false, length = 255)
    private String registeredBy;

    @Column(nullable = false)
    private int flag = 1;  // 1 = active, 0 = deleted


    // Getters and Setters


    public Product(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }
}
