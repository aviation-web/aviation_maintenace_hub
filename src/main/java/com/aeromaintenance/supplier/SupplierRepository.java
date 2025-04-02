package com.aeromaintenance.supplier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository  extends JpaRepository<SupplierModel, Long>{

	 // Fetch suppliers where userAction = "M" and userRole = "QM"
    List<SupplierModel> findByUserActionAndUserRole(String userAction, String userRole);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO supplier_history (form_id, rev, sysdate, supplier_name, address, phone_number, fax, email_id, qm_name, " +
                   "qm_phone_number, qm_email_id, sales_representative_name, sale_phone_number, sale_email_id, core_products, years_in_business, " +
                   "alredy_iso_standard, iso_registered, iso_standard, car_dgca_approval, iso_registration_plans, total_employees, " +
                   "operating_shifts, quality_manual, annual_turnover, quality_assurance_independence, documented_corrective_preventive_actions, " +
                   "quality_record_management, product_meets_specifications, incoming_process_documented, sampling_plan, rece_inspe_results_on_file, " +
                   "trace_Identi_maintenance, material_isolation_procedure, isolating_nonconforming_material, customer_deviation_referred, " +
                   "written_work_instructions, final_inspection_evidence, statistical_methods_used, customer_documents_control, " +
                   "revChange_hand_include_Method, supplier_quality_evaluations, approved_supplier_list, supplier_market_price_competence, " +
                   "certified_test_reports, supplier_on_time_delivery, equipment_calibrated, gauges_certified, gauges_sufficient, " +
                   "adequate_area_safety, housekeeping_procedure, user_Name, user_Role, user_Id, user_Action,checker_By) " + //checker_By
                   "SELECT form_id, rev, sysdate, supplier_name, address, phone_number, fax, email_id, qm_name, " +
                   "qm_phone_number, qm_email_id, sales_representative_name, sale_phone_number, sale_email_id, core_products, years_in_business, " +
                   "alredy_iso_standard, iso_registered, iso_standard, car_dgca_approval, iso_registration_plans, total_employees, " +
                   "operating_shifts, quality_manual, annual_turnover, quality_assurance_independence, documented_corrective_preventive_actions, " +
                   "quality_record_management, product_meets_specifications, incoming_process_documented, sampling_plan, rece_inspe_results_on_file, " +
                   "trace_Identi_maintenance, material_isolation_procedure, isolating_nonconforming_material, customer_deviation_referred, " +
                   "written_work_instructions, final_inspection_evidence, statistical_methods_used, customer_documents_control, " +
                   "'rev_change_hand_include_method', supplier_quality_evaluations, approved_supplier_list, supplier_market_price_competence, " +
                   "certified_test_reports, supplier_on_time_delivery, equipment_calibrated, gauges_certified, gauges_sufficient, " +
                   "adequate_area_safety, housekeeping_procedure, user_Name, user_Role, user_Id, user_Action, :checkerBy " +
                   "FROM supp_regist_temp WHERE form_id = :supplierId", 
           nativeQuery = true)
    int moveToSupplierHistory(@Param("supplierId") Long supplierId, @Param("checkerBy") String checkerBy);

}
