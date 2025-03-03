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

    @Column(nullable = false, length = 255)
    private String productName;

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

    @Column(name = "registration_date", nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate = new Date();

    @Column(nullable = false, length = 255)
    private String registeredBy;

    // Getters and Setters
}
