package com.aeromaintenance.CustomerReg;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    @Size(max = 255, message = "Customer name should not exceed 255 characters")
    private String customerName;

    @NotBlank(message = "Contact person name is required")
    @Size(max = 255, message = "Contact person name should not exceed 255 characters")
    private String contactPersonName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "Phone number should be 12 digits")
    private String phoneNo;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Country code is required")
    @Pattern(regexp = "^[0-9]{2}$", message = "Country code must be exactly 2 digits")
    private String countryCode;

    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid email format")
    private String emailId;

    @NotBlank(message = "Ship To Address 1 is required")
    @Size(max = 500, message = "Address should not exceed 500 characters")
    private String shipToAddress1;

    @Size(max = 500, message = "Address should not exceed 500 characters")
    private String shipToAddress2;

    @Size(max = 500, message = "Address should not exceed 500 characters")
    private String shipToAddress3;

    @NotBlank(message = "Bill To Address is required")
    @Size(max = 500, message = "Address should not exceed 500 characters")
    private String billToAddress;

    @NotBlank(message = "Payment terms are required")
    @Pattern(regexp = "30|60|90|advance payment", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Payment terms must be one of: 30, 60, 90, advance payment")
    private String paymentTerms;

    @NotBlank(message = "GST number is required")
    @Pattern(regexp = "^[0-9A-Z]{15}$", message = "GST number must be 15 characters (alphanumeric uppercase)")
    private String gstNo;

    @NotBlank(message = "Customer type is required")
    @Pattern(regexp = "Airline|MRO|NSOP", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Customer type must be Airline, MRO, or NSOP")
    private String customerType;
}
