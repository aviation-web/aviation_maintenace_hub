package com.aeromaintenance.inspectionReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.aeromaintenance.storeAcceptance.StoreAcc;
import com.aeromaintenance.storeAcceptance.StoreAccRepository;
import com.common.ResponseBean;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InspectionReportService {
	@Autowired
    private InspectionReportRepositoryCustom inspectionReportRepositoryCustom;
	
	@Autowired
	private InspectionReportRepository inspectionReportRepository;

	@Autowired
	private StoreAccRepository storeAccRepository;

	public int approveReport(InspectionReportDto report) {
		 int result=0;
		try {
			
			 //int count=0;
			// if(report.getUserAction().equalsIgnoreCase("2")) {
				 //System.out.println(report.getInspectionReportId());
				 if(!inspectionReportRepositoryCustom.existsByReportId(report.getInspectionReportId())) {
					 result = inspectionReportRepositoryCustom.insertReportForm(report);
//					  if(result>0) {
//						 count=inspectionReportRepositoryCustom.updateReportTemp(report.getUserAction(), report.getInspectionReportId());
//						 }
				 }else {
					 //duplicate records
					 System.out.println("Allredy ReportId present in the database");	
				result= -1;	 
				 }
//			}else {  //if(report.getUserAction().equals("3")) {
//				
//				result =inspectionReportRepositoryCustom.updateEditReportTemp(report.getUserAction(), report.getInspectionReportId());
//				 }
			 
			 
		 }catch(Exception e) {
			 e.printStackTrace();	 
		}
		 
		return result;

	}

	public InspectionReport updateReport(Long id, InspectionReport updateReport) {
		return inspectionReportRepository.findById(id).map(updateExisting -> {
	    	  // Only update the fields that should be updated
	        if (updateReport.getPartNumber() != null) {
	            updateExisting.setPartNumber(updateReport.getPartNumber());
	        }
	        if (updateReport.getPartDesc()!= null) {
	            updateExisting.setPartDesc(updateReport.getPartDesc());
	        }
	        if (updateReport.getPurchaseOrderNo()!= null) {
	            updateExisting.setPurchaseOrderNo(updateReport.getPurchaseOrderNo());
	        }
	        if (updateReport.getSupplierName() != null) {
	            updateExisting.setSupplierName(updateReport.getSupplierName());
	        }
	        if (updateReport.getReportNo() != null) {
	            updateExisting.setReportNo(updateReport.getReportNo());
	        }
	        if (updateReport.getDate() != null) {
	            updateExisting.setDate(updateReport.getDate());
	        }
	        
	        if (updateReport.getQty() != null) {
	            updateExisting.setQty(updateReport.getQty());
	        }
	        
	        if (updateReport.getQtyReceive()!= null) {
	            updateExisting.setQtyReceive(updateReport.getQtyReceive());
	        }
	        
	        if (updateReport.getInvoiceObservation() != null) {
	            updateExisting.setInvoiceObservation(updateReport.getInvoiceObservation());
	        }
	        if (updateReport.getManufacturerCertObservation() != null) {
	            updateExisting.setManufacturerCertObservation(updateReport.getManufacturerCertObservation());
	        }
	        if(updateReport.getSupplierCertObservation()!=null) {
	        	updateExisting.setSupplierCertObservation(updateReport.getSupplierCertObservation());
	        }
	        
	        if (updateReport.getFullTraceabilityObservation() != null) {
	            updateExisting.setFullTraceabilityObservation(updateReport.getFullTraceabilityObservation());
	        }
	        if (updateReport.getBatchNumberObservation() != null) {
	            updateExisting.setBatchNumberObservation(updateReport.getBatchNumberObservation());
	        }
	        if (updateReport.getDateOfManufacturingObservation() != null) {
	            updateExisting.setDateOfManufacturingObservation(updateReport.getDateOfManufacturingObservation());
	        }
	        
	        if (updateReport.getDateOfExpiryObservation() != null) {
	            updateExisting.setDateOfExpiryObservation(updateReport.getDateOfExpiryObservation());
	        }
	        if (updateReport.getSelfLifeObservation() != null) {
	            updateExisting.setSelfLifeObservation(updateReport.getSelfLifeObservation());
	        }
	        if (updateReport.getTdsObservation() != null) {
	            updateExisting.setTdsObservation(updateReport.getTdsObservation());
	            
	        }
	        if (updateReport.getMaterialConditionObservation() != null) {
	            updateExisting.setMaterialConditionObservation(updateReport.getMaterialConditionObservation());
	        }
	        if (updateReport.getSpecificationObservation() != null) {
	            updateExisting.setSpecificationObservation(updateReport.getSpecificationObservation());
	        }
	        if (updateReport.getDocumentObservation() != null) {
	            updateExisting.setDocumentObservation(updateReport.getDocumentObservation());
	        }
	        if (updateReport.getLotAccepted() != null) {
	            updateExisting.setLotAccepted(updateReport.getLotAccepted());
	        }
	        if (updateReport.getRemark() != null) {
	            updateExisting.setRemark(updateReport.getRemark());
	        }
	        if (updateReport.getMakerUserName() != null) {
	            updateExisting.setMakerUserName(updateReport.getMakerUserName());
	        }
	        if (updateReport.getMakerDate() != null) {
	            updateExisting.setMakerDate(updateReport.getMakerDate());
	        }
	        if (updateReport.getUserAction() != null) {
	        	 updateExisting.setUserAction(updateReport.getUserAction());
	        }
	        if (updateReport.getUserRole() != null) {
	            updateExisting.setUserRole(updateReport.getUserRole());
	        }
	        InspectionReport reported=inspectionReportRepository.save(updateExisting);
	        InspectionReportDto reportDto = new InspectionReportDto();
	        BeanUtils.copyProperties(updateExisting, reportDto);
	        int rowsInserted = inspectionReportRepositoryCustom.insertReportForm(reportDto);
	        if(rowsInserted >0) {
	        saveInspectionDataInStore(updateExisting);
	        }
//	        ResponseBean<Void> response=null;
//		     if (rowsInserted >= 1) {
//		    	     reportService.saveInspectionDataInStore(reports);
//		    		 response = new ResponseBean<>("200", "Report Submitted and moved to history successfully", null);	 	        
//		         return ResponseEntity.status(HttpStatus.OK).body(response);
//		     }else if(rowsInserted == -1) {
//		    	response = new ResponseBean<>("409", "Report already exists in the database", null);
//		         return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//		     } else {
//		    	 response= new ResponseBean<>("500", "Failed ", null);
//		         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//		     }
	        //inspectionReportRepositoryCustom.insertReportForm(updateExisting);
	        return reported;
	    }).orElse(null); 
	}

	public List<InspectionReportDto> getAllViewReportList() {
		List<Object[]> rawData = inspectionReportRepository.getRawReportList();
        List<InspectionReportDto> result = new ArrayList<>();

        for (Object[] row : rawData) {
            Long id = ((Number) row[0]).longValue();
            String partNumber = (String) row[1];
            String partDesc = (String) row[2];
            String purchaseOrderNo = (String) row[3];
            String supplierName = (String) row[4];
        	String reportNo = (String) row[5];
        	LocalDate date = ((java.sql.Date) row[6]).toLocalDate();
            Integer qty = (Integer) row[7];
            Integer qtyReceive = (Integer) row[8];
        	String invoiceObservation = (String) row[9];
        	String manufacturerCertObservation = (String) row[10];
        	String supplierCertObservation = (String) row[11];	
        	String fullTraceabilityObservation = (String) row[12];
            String batchNumberObservation = (String) row[13];
            LocalDate dateOfManufacturingObservation = ((java.sql.Date) row[14]).toLocalDate();
            String selfLifeObservation = (String) row[15];
            String tdsObservation = (String) row[16];
            String materialConditionObservation = (String) row[17];
        	String specificationObservation = (String) row[18];
            String documentObservation = (String) row[19];
        	String lotAccepted = (String) row[20];
        	String remark = (String) row[21];
        	String makerUserName = (String) row[22];
        	//String makerUserId = (String) row[22];
        	LocalDate makerDate = row[23] != null ? ((java.sql.Date) row[23]).toLocalDate() : null;
        	String checkerUserName = (String) row[24];
        	//String checkerUserId = (String) row[25];
        	LocalDate checkerDate = row[25] != null ? ((java.sql.Date) row[25]).toLocalDate() : null;
        	LocalDate dateOfExpiryObservation = ((java.sql.Date) row[26]).toLocalDate();
        	//String userRole = (String) row[28];
            result.add(new InspectionReportDto(id, partNumber, partDesc, purchaseOrderNo, supplierName, reportNo,
            		date, qty, qtyReceive,invoiceObservation, manufacturerCertObservation, supplierCertObservation, fullTraceabilityObservation,
            		batchNumberObservation, dateOfManufacturingObservation, dateOfExpiryObservation,selfLifeObservation, tdsObservation, materialConditionObservation,
            		specificationObservation, documentObservation, lotAccepted, remark, makerUserName, "",
            		makerDate, checkerUserName, "", checkerDate, "", "" ,""));
        }

        return result;
	}

	public void saveInspectionDataInStore(InspectionReport dto) {
		StoreAcc storeAcc = StoreAccMapper.fromInspectionReport(dto);
		//int quantity = storeAccRepository.getReceiveQuantityByPartNO(dto.getPartNumber());
		//storeAcc.setQuantity(storeAcc.getQuantity() + quantity);
		storeAccRepository.save(storeAcc);
	}

	@Transactional
	public void updateMrnNoStatus(String reportNo) {
		
		inspectionReportRepository.updateMrnNoStatus(reportNo);
	}

	public void updateInventoryCurrentStoke(InspectionReport reports) {
		if(inspectionReportRepositoryCustom.checkPartNoIsPresentInStore(reports.getPartNumber())){
			int quantity = inspectionReportRepositoryCustom.getCurrentStokeFromInventory(reports.getPartNumber());
			int currentStoke = quantity + reports.getQtyReceive();
			int updateQuantity = inspectionReportRepositoryCustom.UpdateCurrentQuantity(reports.getPartNumber(),currentStoke);
			
		}else {
			
			int rowInsert = inspectionReportRepositoryCustom.insertInStoreInventory(reports);
			
		}
	}

}
