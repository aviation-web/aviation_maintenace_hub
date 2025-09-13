package com.aeromaintenance.supplier;

import java.math.BigDecimal;
import java.sql.Date;

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
import lombok.ToString;



@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supp_regist_temp")
public class SupplierModel {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "rev")
    private Integer rev;
    
    @Column(name = "sysdate")
    private Date sysdate;
    
    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fax")
    private String faxNum;

    @Column(name = "email_id")
    private String email;

    @Column(name = "qm_name")
    private String qualityManagerName;
    
    @Column(name = "qm_phone_number")
    private String qualityManagerPhoneNumber;

    @Column(name = "qm_email_id")
    private String qualityManagerEmailId;

    @Column(name = "sales_representative_name")
    private String saleRepresentativeName;
    
    @Column(name = "sale_phone_number")
    private String saleRepresentativePhoneNumber;

    @Column(name = "sale_email_id")
    private String saleRepresentativeEmailId;

    @Column(name = "core_products")
    private String coreProcess;

    @Column(name = "years_in_business")
    private String workYear;

    @Column(name = "alredy_iso_standard")
    private String areYouIsoRegistered;
    
    @Column(name = "iso_registered") 
    private String isoRegistered;

    @Column(name = "iso_standard")
    private String isoStandard;

    @Column(name = "car_dgca_approval")
    private String carDgcaApproval;

    @Column(name = "iso_registration_plans")
    private String isoRegistrationPlans;

    @Column(name = "total_employees")
    private Integer numEmp;

    @Column(name = "operating_shifts")
    private Integer numOpeShift;

    @Column(name = "quality_manual")
    private String quaManual;

    @Column(name = "annual_turnover")
    private BigDecimal turnOver;

    @Column(name = "quality_assurance_independence")
    private String independenceManuf ;//qualityAssuranceIndependence;

    @Column(name = "documented_corrective_preventive_actions")
    private String documentedOperative; //nonConMaterial

    @Column(name = "quality_record_management")
    private String documentedProcedure;

    @Column(name = "product_meets_specifications")
    private String productShipment;

    @Column(name = "incoming_process_documented")
    private String processDocumented;

    @Column(name = "sampling_plan")
    private String samplingIncomingInsp;

    @Column(name = "rece_inspe_results_on_file")
    private String receivingInspectionResultsOnFile;

    @Column(name = "trace_Identi_maintenance")
    private String identificationMaintained; //traceabilityIdentificationMaintenance;

    @Column(name = "material_isolation_procedure")
    private String sepInsMaterial;  //sepInsMaterial

    @Column(name = "isolating_nonconforming_material")
    private String nonConMaterial;

    @Column(name = "customer_deviation_referred")
    private String affectCusReq;

    @Column(name = "written_work_instructions")
    private String writtenWorkInstructionsAvaibleInStation;
    
    @Column(name = "final_inspection_evidence")
    private String finalInspectionEvidence;
    
    @Column(name = "statistical_methods_used")
    private String statisMethod;

    @Column(name = "payment_terms")
    private String paymentTerms;

    @Column(name = "customer_documents_control")
    private String suppliedDocument;

    @Column(name = "revChange_hand_includeMethod")
    private String includeMethod;
    
   
    @Column(name = "supplier_quality_evaluations")
    private String qualityCapabilities;

    @Column(name = "approved_supplier_list")
    private String approvedSupplierList;
    
    @Column(name = "supplier_market_price_competence")
    private String marketPrice;
    

    @Column(name = "certified_test_reports")
    private String certifiedTestReports;

    @Column(name = "supplier_on_time_delivery")
    private String supplierOnTimeDelivery;

    @Column(name = "equipment_calibrated")
    private String equipCalibrated;
    
    @Column(name = "gauges_certified")
    private String recalibration;

    @Column(name = "gauges_sufficient")
    private String scopeOfWork;
    
    
    @Column(name = "adequate_area_safety")
    private String safetyProgram;

    @Column(name = "housekeeping_procedure")
    private String houseKeeping; 
    
    @Column(name="userName")
    private String userName;
    
    @Column(name="userRole")
	private String userRole;
    
    @Column(name="userId")
	private String userId;
    
    @Column(name="userAction")
    private String userAction;
    
    @Column(name="remark")
    private String remark;

}
