package com.aeromaintenance.inspectionReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.aeromaintenance.storeAcceptance.StoreAcc;
import com.aeromaintenance.storeAcceptance.StoreAccRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
		try {
			 int result=0;
			 int count=0;
			 if(report.getUserAction().equalsIgnoreCase("2")) {
				 System.out.println(report.getInspectionReportId());
				 if(!inspectionReportRepositoryCustom.existsByReportId(report.getInspectionReportId())) {
					 result = inspectionReportRepositoryCustom.insertReportForm(report);
					  if(result>0) {
						 count=inspectionReportRepositoryCustom.updateReportTemp(report.getUserAction(), report.getInspectionReportId());
						 }
				 }else {
					 //duplicate records
					 System.out.println("Allredy ReportId present in the database");	
				return -1;	 
				 }
			}else {  //if(report.getUserAction().equals("3")) {
				
				result =inspectionReportRepositoryCustom.updateEditReportTemp(report.getUserAction(), report.getInspectionReportId());
				 }
			 return result;
			 
		 }catch(Exception e) {
			 e.printStackTrace();	 }
		 
		 return 0;

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

	        return inspectionReportRepository.save(updateExisting);
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
		storeAccRepository.save(storeAcc);
	}

}
