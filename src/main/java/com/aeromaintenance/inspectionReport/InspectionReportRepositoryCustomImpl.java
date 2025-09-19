package com.aeromaintenance.inspectionReport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.aeromaintenance.supplier.SupplierDto;

@Transactional
@Repository
public class InspectionReportRepositoryCustomImpl implements InspectionReportRepositoryCustom {
	 @PersistenceContext
	 private EntityManager entityManager;
	 
	 public int insertReportForm(InspectionReportDto form) {
		    Query query = entityManager.createNativeQuery(" INSERT INTO inspection_report_history (report_no, part_desc, "+
			    " part_number, batch_number_observation, purchase_order_no, qty, qty_receive, supplier_name, "+
			    " date, maker_date, checker_date, date_of_manufacturing_observation, "+
			    " maker_user_id, maker_user_name, checker_user_id, checker_user_name, user_role, user_action, "+
			    " document_observation, invoice_observation, manufacturer_cert_observation, supplier_cert_observation, "+
			    " tds_observation, specification_observation, material_condition_observation, self_life_observation, "+
			    " full_traceability_observation, lot_accepted, remark, inspection_report_id, date_of_expiry_observation, document_path "+
			    " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )"	   
			);
		    
		    query.setParameter(1, form.getReportNo());
		    query.setParameter(2, form.getPartDesc());
		    query.setParameter(3, form.getPartNumber());
		    query.setParameter(4, form.getBatchNumberObservation());
		    query.setParameter(5, form.getPurchaseOrderNo());
		    query.setParameter(6, form.getQty());
		    query.setParameter(7, form.getQtyReceive());
		    query.setParameter(8, form.getSupplierName());

		    query.setParameter(9, form.getDate());
		    query.setParameter(10, form.getMakerDate());
		    query.setParameter(11, form.getCheckerDate());

		    query.setParameter(12, form.getDateOfManufacturingObservation());
		    query.setParameter(13, form.getMakerUserId());
		    query.setParameter(14, form.getMakerUserName());

		    query.setParameter(15, form.getCheckerUserId());
		    query.setParameter(16, form.getCheckerUserName());
		    query.setParameter(17, form.getUserRole());
		    query.setParameter(18, form.getUserAction());
		    query.setParameter(19, form.getDocumentObservation());

		    query.setParameter(20, form.getInvoiceObservation());
		    query.setParameter(21, form.getManufacturerCertObservation());
		    query.setParameter(22, form.getSupplierCertObservation());
		    query.setParameter(23, form.getTdsObservation());
		    query.setParameter(24, form.getSpecificationObservation());

		    query.setParameter(25, form.getMaterialConditionObservation());
		    query.setParameter(26, form.getSelfLifeObservation());
		    query.setParameter(27, form.getFullTraceabilityObservation());
		    query.setParameter(28, form.getLotAccepted());
		    query.setParameter(29, form.getRemark());
		    query.setParameter(30, form.getInspectionReportId());
		    query.setParameter(31, form.getDateOfExpiryObservation());
		    query.setParameter(32, form.getDocumentPath());
		    
		    int result =  query.executeUpdate();
		     return result;
	 	}
	 
	 public boolean existsByReportId(Long reportId) {
		    Number count = (Number) entityManager.createNativeQuery(
		            "SELECT COUNT(*) FROM inspection_report_history WHERE inspection_report_id = ?")
		            .setParameter(1, reportId)
		            .getSingleResult();
		    System.out.println("count is :-" + count);	
		    return count.intValue() > 0;
		}
	 
	 public int updateReportTemp(String actionCode,Long reportId) {
		 int result=0;
		 try {
			  Query query = entityManager.createNativeQuery("UPDATE inspection_report SET user_action = ? WHERE inspection_report_id =?");
			  query.setParameter(1, actionCode);
			 query.setParameter(2, reportId);
			 result=query.executeUpdate();
		}catch(Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	 }
	 
	 public int updateEditReportTemp(String actionCode, Long reportId) {
		 int result =0;
		 try {
			 Query query = entityManager.createNativeQuery("UPDATE inspection_report SET user_action = ? WHERE inspection_report_id =?");
		     query.setParameter(1,actionCode);
		     query.setParameter(2,reportId);
		     result = query.executeUpdate();
		     System.out.println("UserAction:-" + actionCode);
		     }catch(Exception e) {
		    	 e.printStackTrace();
		     }
		 System.out.println(result);
		 return result;
	 }

	@Override
	public boolean checkPartNoIsPresentInStore(String partNumber) {
		Number count = (Number) entityManager.createNativeQuery(
	            "SELECT COUNT(*) FROM store_inventory WHERE part_number = ?")
	            .setParameter(1, partNumber)
	            .getSingleResult();
	    System.out.println("count is :-" + count);	
	    return count.intValue() > 0;
	}

	@Override
	public int getCurrentStokeFromInventory(String partNumber) {
		Number currentStoke = (Number) entityManager.createNativeQuery(
	            "SELECT quantity FROM store_inventory WHERE trim(part_number) = ?")
	            .setParameter(1, partNumber)
	            .getSingleResult();
	    System.out.println("currentStoke is :-" + currentStoke);	
	    
			return currentStoke.intValue();
	}

	@Override
	public int UpdateCurrentQuantity(String partNumber, int currentStoke) {
			 int result=0;
			 try {
				  Query query = entityManager.createNativeQuery("UPDATE store_inventory SET quantity = ? WHERE part_number =?");
				  query.setParameter(1, currentStoke);
				 query.setParameter(2, partNumber);
				 result=query.executeUpdate();
			}catch(Exception e) {
				 e.printStackTrace();
			 }
			 return result;
		 }

	@Override
	public int insertInStoreInventory(InspectionReport reports) {
		Query query = entityManager.createNativeQuery(" INSERT INTO store_inventory (part_number, part_description, quantity"+
			    " ) VALUES (?, ?, ? )"	   
			);
		    
		    query.setParameter(1, reports.getPartNumber());
		    query.setParameter(2, reports.getPartDesc());
		    query.setParameter(3, reports.getQtyReceive());
		    int result =  query.executeUpdate();
		     return result;
	}

	@Override
	public int getRequiredQtyFromPurchaseOrder(String purchaseOrderNo, String partNumber) {
		Number result = (Number) entityManager.createNativeQuery(
	            "SELECT current_stoke FROM purchase_order WHERE part_number = ? AND po_number = ?")
	            .setParameter(1, partNumber)
	            .setParameter(2, purchaseOrderNo)
	            .getSingleResult();
		int requiredQuantity = result != null ? result.intValue() : 0;
		System.out.println("count is :-" + requiredQuantity);
	    
			return requiredQuantity;
	}


	@Override
	public int updatePoStatus(String status, String poNumber, String partNumber) {
		int result=0;
		 try {
			  Query query = entityManager.createNativeQuery("UPDATE purchase_order SET status = ? WHERE part_number = ? AND po_number = ?");
			  query.setParameter(1, status);
			 query.setParameter(2, partNumber);
			 query.setParameter(3, poNumber);
			 result=query.executeUpdate();
		}catch(Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	}
	 }
