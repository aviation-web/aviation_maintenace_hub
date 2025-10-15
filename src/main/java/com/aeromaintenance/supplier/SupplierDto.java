package com.aeromaintenance.supplier;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SupplierDto {
	
    //@Column(name = "form_id")
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//    private Long formId;
	
//    @Column(name = "supplier_Id")
    private Long supplierId;

   //@Column(name = "rev")
    private Integer rev;
    
    //@Column(name = "sysdate")
    private Date sysdate;
    
     @NotBlank(message = "Supplier name cannot be blank")
    @Size(max = 255, message = "Supplier name cannot exceed 255 characters")
   // @Column(name = "supplier_name")
    private String supplierName;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
   // @Column(name = "address")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
   // @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 20, message = "Fax cannot exceed 20 characters")
   // @Column(name = "fax")
    private String faxNum;

    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email ID cannot exceed 255 characters")
   // @Column(name = "email_id")
    private String email;

    @Size(max = 255, message = "Quality manager name cannot exceed 255 characters")
   // @Column(name = "qm_name")
    private String qualityManagerName;

    @Size(max = 15, message = "Quality manager phone number cannot exceed 15 characters")
  //  @Column(name = "qm_phone_number")
    private String qualityManagerPhoneNumber;

    @Email(message = "Quality manager email should be valid")
    @Size(max = 255, message = "Quality manager email ID cannot exceed 255 characters")
    //@Column(name = "qm_email_id")
    private String qualityManagerEmailId;

    @Size(max = 255, message = "Sales representative name cannot exceed 255 characters")
   // @Column(name = "sales_representative_name")
    private String saleRepresentativeName;
    
    @Size(max = 15, message = "Sales phone number cannot exceed 15 characters")
   // @Column(name = "sale_phone_number")
    private String saleRepresentativePhoneNumber;

    @Email(message = "Sales representative email should be valid")
    @Size(max = 255, message = "Sales representative email ID cannot exceed 255 characters")
  //  @Column(name = "sale_email_id")
    private String saleRepresentativeEmailId;

    @Size(max = 500, message = "Core products cannot exceed 500 characters")
    //@Column(name = "core_products")
    private String coreProcess;

    @Size(max = 4, message = "Years in business cannot exceed 4 characters")
    //@Column(name = "years_in_business")
    private String workYear;

    @Size(max = 255, message = "ISO iso_registered cannot exceed 255 characters")
   // @Column(name = "alredy_iso_standard")
    private String areYouIsoRegistered;
    
    //@Column(name = "iso_registered") 
    private String isoRegistered;

    @Size(max = 255, message = "ISO standard cannot exceed 255 characters")
   // @Column(name = "iso_standard")
    private String isoStandard;

    @Size(max = 255, message = "DGCA approval cannot exceed 255 characters")
  //  @Column(name = "car_dgca_approval")
    private String carDgcaApproval;

    @Size(max = 255, message = "ISO registration plans cannot exceed 255 characters")
   // @Column(name = "iso_registration_plans")
    private String isoRegistrationPlans;

    @Min(value = 1, message = "Total employees must be greater than 0")
   // @Column(name = "total_employees")
    private Integer numEmp;

    @Min(value = 1, message = "Operating shifts must be greater than 0")
   // @Column(name = "operating_shifts")
    private Integer numOpeShift;

    @Size(max = 255, message = "Quality manual cannot exceed 255 characters")
   // @Column(name = "quality_manual")
    private String quaManual;

    @DecimalMin(value = "0.0", message = "Annual turnover must be greater than or equal to 0")
   // @Column(name = "annual_turnover")
    private BigDecimal turnOver;

    @Size(max = 255, message = "Quality assurance independence cannot exceed 255 characters")
   // @Column(name = "quality_assurance_independence")
    private String independenceManuf ;//qualityAssuranceIndependence;

    @Size(max = 255, message = "Documented corrective actions cannot exceed 255 characters")
   // @Column(name = "documented_corrective_preventive_actions")
    private String documentedOperative; //nonConMaterial

    @Size(max = 255, message = "Quality record management cannot exceed 255 characters")
   // @Column(name = "quality_record_management")
    private String documentedProcedure;

    @Size(max = 255, message = "Product meets specifications cannot exceed 255 characters")
   // @Column(name = "product_meets_specifications")
    private String productShipment;

    @Size(max = 255, message = "Incoming process documented cannot exceed 255 characters")
   // @Column(name = "incoming_process_documented")
    private String processDocumented;

    @Size(max = 255, message = "Sampling plan cannot exceed 255 characters")
   // @Column(name = "sampling_plan")
    private String samplingIncomingInsp;

    @Size(max = 255, message = "Receiving inspection results on file cannot exceed 255 characters")
    //@Column(name = "rece_inspe_results_on_file")
    private String receivingInspectionResultsOnFile;

    @Size(max = 255, message = "Traceability maintenance cannot exceed 255 characters")
   // @Column(name = "trace_Identi_maintenance")
    private String identificationMaintained; //traceabilityIdentificationMaintenance;

    @Size(max = 255, message = "Material isolation procedure cannot exceed 255 characters")
    //@Column(name = "material_isolation_procedure")
    private String sepInsMaterial;  //sepInsMaterial

    @Size(max = 255, message = "Isolating nonconforming material cannot exceed 255 characters")
   // @Column(name = "isolating_nonconforming_material")
    private String nonConMaterial;

    @Size(max = 255, message = "Customer deviation referred cannot exceed 255 characters")
   // @Column(name = "customer_deviation_referred")
    private String affectCusReq;

    @Size(max = 255, message = "Written work instructions cannot exceed 255 characters")
    //@Column(name = "written_work_instructions")
    private String writtenWorkInstructionsAvaibleInStation;
    
    @Size(max = 255, message = "Final inspection evidence cannot exceed 255 characters")
    //@Column(name = "final_inspection_evidence")
    private String finalInspectionEvidence;
    
    @Size(max = 255, message = "Statistical methods used cannot exceed 255 characters")
    //@Column(name = "statistical_methods_used")
    private String statisMethod;

    @Size(max = 50, message = "Payment Terms cannot exceed 50 characters")
    // @Column(name = "payment_terms")
    private String paymentTerms;

    @Size(max = 255, message = "Customer documents control cannot exceed 255 characters")
    //@Column(name = "customer_documents_control")
    private String suppliedDocument;

    @Size(max = 255, message = "Revision change handling cannot exceed 255 characters")
   // @Column(name = "revChange_hand_includeMethod")
    private String includeMethod;
    
   
    @Size(max = 255, message = "Supplier quality evaluations cannot exceed 255 characters")
    //@Column(name = "supplier_quality_evaluations")
    private String qualityCapabilities;

    @Size(max = 255, message = "Approved supplier list cannot exceed 255 characters")
    //@Column(name = "approved_supplier_list")
    private String approvedSupplierList;
    
    @Size(max = 255, message = "Supplier market price competence cannot exceed 255 characters")
   // @Column(name = "supplier_market_price_competence")
    private String marketPrice;
    

    @Size(max = 255, message = "Certified test reports cannot exceed 255 characters")
  //  @Column(name = "certified_test_reports")
    private String certifiedTestReports;

    @Size(max = 255, message = "Supplier on-time delivery cannot exceed 255 characters")
   // @Column(name = "supplier_on_time_delivery")
    private String supplierOnTimeDelivery;

    @Size(max = 255, message = "Equipment calibrated cannot exceed 255 characters")
   // @Column(name = "equipment_calibrated")
    private String equipCalibrated;
    
   @Size(max = 255, message = "Gauges certified cannot exceed 255 characters")
   // @Column(name = "gauges_certified")
    private String recalibration;

    @Size(max = 255, message = "Gauges sufficient cannot exceed 255 characters")
   // @Column(name = "gauges_sufficient")
    private String scopeOfWork;
    
    
    @Size(max = 255, message = "Adequate area safety cannot exceed 255 characters")
   // @Column(name = "adequate_area_safety")
    private String safetyProgram;

    @Size(max = 255, message = "Housekeeping procedure cannot exceed 255 characters")
   // @Column(name = "housekeeping_procedure")
    @Column(name="remark")
    private String remark;
    
    private String houseKeeping;
    private String userName;
    private String userRole;
    private String userId;
    private String userAction;
    private String checkerBy;



 @NotBlank(message = "Sale Representative Country Code is required")
 @Pattern(regexp = "^[0-9]{1,4}$", message = "Sale Representative Country Code must be numeric (1 to 4 digits)")
 private String saleRepresentativeCountryCode;

 @NotBlank(message = "Quality Manager Country Code is required")
 @Pattern(regexp = "^[0-9]{1,4}$", message = "Quality Manager Country Code must be numeric (1 to 4 digits)")
 private String qualityManagerCountryCode;

 @NotBlank(message = "Country Code is required")
 @Pattern(regexp = "^[0-9]{1,4}$", message = "Country Code must be numeric (1 to 4 digits)")
 private String countryCode;
}
