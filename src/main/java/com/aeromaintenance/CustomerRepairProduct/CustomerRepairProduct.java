package com.aeromaintenance.CustomerRepairProduct;


import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_repair_products")
public class CustomerRepairProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product_serial_numbers",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "serial_number")
    private List<String> productSerialNumbers;

    private String productDescription;

    private String unitOfMeasurement;

    private String oem;

    private String cmmRefNo;

    private LocalDate date; // system date at creation

    private String registerBy;
}

