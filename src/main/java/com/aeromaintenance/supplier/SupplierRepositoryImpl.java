package com.aeromaintenance.supplier;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class SupplierRepositoryImpl {
	
	 @PersistenceContext
	    private EntityManager entityManager;

	public int insertSupplierForm(SupplierDto form) {
		Query query = entityManager.createNativeQuery(
				"INSERT INTO supplier_history1 (" +
						"rev, sysdate, supplier_name,vendor_type, address, phone_number, fax, email_id, " +
						"qm_name, qm_phone_number, qm_email_id, quality_manager_country_code, " +
						"sales_representative_name, sale_phone_number, sale_email_id, sale_representative_country_code, " +
						"core_products, years_in_business, iso_registered, iso_standard, " +
						"car_dgca_approval, iso_registration_plans, total_employees, operating_shifts, quality_manual, " +
						"annual_turnover, quality_assurance_independence, documented_corrective_preventive_actions, " +
						"quality_record_management, product_meets_specifications, incoming_process_documented, sampling_plan, " +
						"rece_inspe_results_on_file, trace_Identi_maintenance, material_isolation_procedure, " +
						"isolating_nonconforming_material, customer_deviation_referred, written_work_instructions, " +
						"final_inspection_evidence, statistical_methods_used, customer_documents_control, " +
						"rev_change_hand_include_method, supplier_quality_evaluations, approved_supplier_list, " +
						"supplier_market_price_competence, certified_test_reports, supplier_on_time_delivery, " +
						"equipment_calibrated, gauges_certified, gauges_sufficient, adequate_area_safety, " +
						"housekeeping_procedure, user_Name, user_Role, user_Id, user_Action, checker_by, " +
						"supplier_id, country_code, iso_certificate" +
						") VALUES (" +
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);

		// Set all parameters as strings
		query.setParameter(1, form.getRev());
		query.setParameter(2, form.getSysdate());
		query.setParameter(3, form.getSupplierName());
		query.setParameter(4, form.getAddress());
		query.setParameter(5, form.getPhoneNumber());
		query.setParameter(6, form.getFaxNum());
		query.setParameter(7, form.getEmail());

		query.setParameter(8, form.getQualityManagerName());
		query.setParameter(9, form.getQualityManagerPhoneNumber());
		query.setParameter(10, form.getQualityManagerEmailId());
		query.setParameter(11, form.getQualityManagerCountryCode());

		query.setParameter(12, form.getSaleRepresentativeName());
		query.setParameter(13, form.getSaleRepresentativePhoneNumber());
		query.setParameter(14, form.getSaleRepresentativeEmailId());
		query.setParameter(15, form.getSaleRepresentativeCountryCode());

		query.setParameter(16, form.getCoreProcess());
		query.setParameter(17, form.getWorkYear());
		query.setParameter(18, form.getIsoRegistered());
		query.setParameter(19, form.getIsoStandard());

		query.setParameter(20, form.getCarDgcaApproval());
		query.setParameter(21, form.getIsoRegistrationPlans());
		query.setParameter(22, form.getNumEmp() != null ? form.getNumEmp().toString() : null);
		query.setParameter(23, form.getNumOpeShift() != null ? form.getNumOpeShift().toString() : null);
		query.setParameter(24, form.getQuaManual());

		query.setParameter(25, form.getTurnOver() != null ? form.getTurnOver().toString() : null);
		query.setParameter(26, form.getIndependenceManuf());
		query.setParameter(27, form.getDocumentedOperative());
		query.setParameter(28, form.getDocumentedProcedure());
		query.setParameter(29, form.getProductShipment());

		query.setParameter(30, form.getProcessDocumented());
		query.setParameter(31, form.getSamplingIncomingInsp());
		query.setParameter(32, form.getReceivingInspectionResultsOnFile());
		query.setParameter(33, form.getIdentificationMaintained());
		query.setParameter(34, form.getSepInsMaterial());
		query.setParameter(35, form.getNonConMaterial());
		query.setParameter(36, form.getAffectCusReq());

		query.setParameter(37, form.getWrittenWorkInstructionsAvaibleInStation());
		query.setParameter(38, form.getFinalInspectionEvidence());
		query.setParameter(39, form.getStatisMethod());

		query.setParameter(40, form.getSuppliedDocument());
		query.setParameter(41, form.getIncludeMethod());
		query.setParameter(42, form.getQualityCapabilities());
		query.setParameter(43, form.getApprovedSupplierList());
		query.setParameter(44, form.getMarketPrice());

		query.setParameter(45, form.getCertifiedTestReports());
		query.setParameter(46, form.getSupplierOnTimeDelivery());
		query.setParameter(47, form.getEquipCalibrated());
		query.setParameter(48, form.getRecalibration());
		query.setParameter(49, form.getScopeOfWork());

		query.setParameter(50, form.getSafetyProgram());
		query.setParameter(51, form.getHouseKeeping());
		query.setParameter(52, form.getUserName());
		query.setParameter(53, form.getUserRole());
		query.setParameter(54, form.getUserId());
		query.setParameter(55, form.getUserAction());
		query.setParameter(56, form.getCheckerBy());
		query.setParameter(57, form.getSupplierId() != null ? form.getSupplierId().toString() : null);
		query.setParameter(58, form.getCountryCode());
		query.setParameter(59, form.getIsoCertificate());
		query.setParameter(60, form.getVendorTypes());

		return query.executeUpdate();
	}







	public int updateSupplierTemp(String actionCode,Long supplierId) {
		 int result=0;
		 try {
			  Query query = entityManager.createNativeQuery("UPDATE supp_regist_temp SET user_action = ? WHERE supplier_id =?");
			  query.setParameter(1, actionCode);
			 query.setParameter(2, supplierId);
			 result=query.executeUpdate();
		}catch(Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	 }
	 
	 public boolean existsBySupplierId(Long supplierId) {
		    Number count = (Number) entityManager.createNativeQuery(
		            "SELECT COUNT(*) FROM supplier_history1 WHERE supplier_id = ?")
		            .setParameter(1, supplierId)
		            .getSingleResult();
		    System.out.println("count is :-" + count);	
		    return count.intValue() > 0;
		}

	 
	 
	 public int updateEditSupplierTemp(String actionCode, Long supplierId) {
		 int result =0;
		 try {
			 Query query = entityManager.createNativeQuery("UPDATE supp_regist_temp SET user_action = ? WHERE supplier_id =?");
		     query.setParameter(1,actionCode);
		     query.setParameter(2,supplierId);
		     result = query.executeUpdate();
		     System.out.println("UserAction:-" + actionCode);
		     }catch(Exception e) {
		    	 e.printStackTrace();
		     }
		 System.out.println(result);
		 return result;
	 }
//	  public int updateEditSupplierTemp(Long supplierId,String action) {
//		  int count=0;
//		  try {
//			  Query query=entityManager.createQuery("UPDATE SuppRegist_temp set user_action=? where supplier_id=?");
//			  query.setParameter(1, action);
//			  query.setParameter(2, supplierId);
//			  count=query.executeUpdate();
//		    }catch(Exception e) {
//			  e.printStackTrace();
//			  }
//		  System.out.println("count  is :-" +count);
//		  return count;
//	  }
	 
	 public int updateEditSupplierTemp(Long supplierId, String action) {
		    int count = 0;
		    try {
		        Query query = entityManager.createQuery(
		            "UPDATE SupplierTempEntity s SET s.userAction = :action WHERE s.supplierId = :supplierId"
		        );
		        query.setParameter("action", action);
		        query.setParameter("supplierId", supplierId);

		        count = query.executeUpdate();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    System.out.println("Count is: " + count);
		    return count;
		}

}
