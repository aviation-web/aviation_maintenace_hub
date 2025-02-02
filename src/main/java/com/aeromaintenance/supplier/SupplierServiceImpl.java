package com.aeromaintenance.supplier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
    private SupplierRepository supplierRepository;
	
	@Override
	public SupplierModel saveSupplier(SupplierModel supplierModel) {
		 return supplierRepository.save(supplierModel);
	}
	@Override
	public List<SupplierModel> getAllProducts() {
		 return supplierRepository.findAll();
	}
	@Override
	public SupplierModel getSupplierById(Long id) {
		return supplierRepository.findById(id).orElse(null);
	}
	@Override
	public void deleteSupplierById(Long id) {
		supplierRepository.deleteById(id);
	  }

	
	public SupplierModel updateSupplierModel(Long id, SupplierModel supplierModel) {
	    return supplierRepository.findById(id).map(updateExisting -> {
	    	  // Only update the fields that should be updated
	        if (supplierModel.getSupplierName() != null) {
	            updateExisting.setSupplierName(supplierModel.getSupplierName());
	        }
	        if (supplierModel.getAddress() != null) {
	            updateExisting.setAddress(supplierModel.getAddress());
	        }
	        if (supplierModel.getPhoneNumber() != null) {
	            updateExisting.setPhoneNumber(supplierModel.getPhoneNumber());
	        }
	        if (supplierModel.getFax() != null) {
	            updateExisting.setFax(supplierModel.getFax());
	        }
	        if (supplierModel.getEmailId() != null) {
	            updateExisting.setEmailId(supplierModel.getEmailId());
	        }
	        if (supplierModel.getQualityManagerName() != null) {
	            updateExisting.setQualityManagerName(supplierModel.getQualityManagerName());
	        }
	        if (supplierModel.getQualityManagerPhoneNumber() != null) {
	            updateExisting.setQualityManagerPhoneNumber(supplierModel.getQualityManagerPhoneNumber());
	        }
	        if (supplierModel.getQualityManagerEmailId() != null) {
	            updateExisting.setQualityManagerEmailId(supplierModel.getQualityManagerEmailId());
	        }
	        if (supplierModel.getSalesRepresentativeName() != null) {
	            updateExisting.setSalesRepresentativeName(supplierModel.getSalesRepresentativeName());
	        }
	        if (supplierModel.getSalePhoneNumber() != null) {
	            updateExisting.setSalePhoneNumber(supplierModel.getSalePhoneNumber());
	        }
	        if (supplierModel.getSaleEmailId() != null) {
	            updateExisting.setSaleEmailId(supplierModel.getSaleEmailId());
	        }
	        if (supplierModel.getCoreProducts() != null) {
	            updateExisting.setCoreProducts(supplierModel.getCoreProducts());
	        }
	        if (supplierModel.getYearsInBusiness() != null) {
	            updateExisting.setYearsInBusiness(supplierModel.getYearsInBusiness());
	        }
	        if (supplierModel.getIsoRegistered() != null) {
	            updateExisting.setIsoRegistered(supplierModel.getIsoRegistered());
	        }
	        if (supplierModel.getIsoStandard() != null) {
	            updateExisting.setIsoStandard(supplierModel.getIsoStandard());
	        }
	        if (supplierModel.getCarDgcaApproval() != null) {
	            updateExisting.setCarDgcaApproval(supplierModel.getCarDgcaApproval());
	        }
	        if (supplierModel.getIsoRegistrationPlans() != null) {
	            updateExisting.setIsoRegistrationPlans(supplierModel.getIsoRegistrationPlans());
	        }
	        if (supplierModel.getTotalEmployees() != null) {
	            updateExisting.setTotalEmployees(supplierModel.getTotalEmployees());
	        }
	        if (supplierModel.getOperatingShifts() != null) {
	            updateExisting.setOperatingShifts(supplierModel.getOperatingShifts());
	        }
	        if (supplierModel.getQualityManual() != null) {
	            updateExisting.setQualityManual(supplierModel.getQualityManual());
	        }
	        if (supplierModel.getAnnualTurnover() != null) {
	            updateExisting.setAnnualTurnover(supplierModel.getAnnualTurnover());
	        }
	        if (supplierModel.getQualityAssuranceIndependence() != null) {
	            updateExisting.setQualityAssuranceIndependence(supplierModel.getQualityAssuranceIndependence());
	        }
	        if (supplierModel.getDocumentedCorrectivePreventiveActions() != null) {
	            updateExisting.setDocumentedCorrectivePreventiveActions(supplierModel.getDocumentedCorrectivePreventiveActions());
	        }
	        if (supplierModel.getQualityRecordManagement() != null) {
	            updateExisting.setQualityRecordManagement(supplierModel.getQualityRecordManagement());
	        }
	        if (supplierModel.getProductMeetsSpecifications() != null) {
	            updateExisting.setProductMeetsSpecifications(supplierModel.getProductMeetsSpecifications());
	        }
	        if (supplierModel.getIncomingProcessDocumented() != null) {
	            updateExisting.setIncomingProcessDocumented(supplierModel.getIncomingProcessDocumented());
	        }
	        if (supplierModel.getSamplingPlan() != null) {
	            updateExisting.setSamplingPlan(supplierModel.getSamplingPlan());
	        }
	        if (supplierModel.getReceivingInspectionResultsOnFile() != null) {
	            updateExisting.setReceivingInspectionResultsOnFile(supplierModel.getReceivingInspectionResultsOnFile());
	        }
	        if (supplierModel.getTraceabilityIdentificationMaintenance() != null) {
	            updateExisting.setTraceabilityIdentificationMaintenance(supplierModel.getTraceabilityIdentificationMaintenance());
	        }
	        if (supplierModel.getMaterialIsolationProcedure() != null) {
	            updateExisting.setMaterialIsolationProcedure(supplierModel.getMaterialIsolationProcedure());
	        }
	        if (supplierModel.getIsolatingNonconformingMaterial() != null) {
	            updateExisting.setIsolatingNonconformingMaterial(supplierModel.getIsolatingNonconformingMaterial());
	        }
	        if (supplierModel.getCustomerDeviationReferred() != null) {
	            updateExisting.setCustomerDeviationReferred(supplierModel.getCustomerDeviationReferred());
	        }
	        if (supplierModel.getEquipmentCalibrated() != null) {
	            updateExisting.setEquipmentCalibrated(supplierModel.getEquipmentCalibrated());
	        }
	        if (supplierModel.getGaugesCertified() != null) {
	            updateExisting.setGaugesCertified(supplierModel.getGaugesCertified());
	        }
	        if (supplierModel.getGaugesSufficient() != null) {
	            updateExisting.setGaugesSufficient(supplierModel.getGaugesSufficient());
	        }
	        if (supplierModel.getWrittenWorkInstructionsAvaibleInStation() != null) {
	            updateExisting.setWrittenWorkInstructionsAvaibleInStation(supplierModel.getWrittenWorkInstructionsAvaibleInStation());
	        }
	        if (supplierModel.getFinalInspectionEvidence() != null) {
	            updateExisting.setFinalInspectionEvidence(supplierModel.getFinalInspectionEvidence());
	        }
	        if (supplierModel.getStatisticalMethodsUsed() != null) {
	            updateExisting.setStatisticalMethodsUsed(supplierModel.getStatisticalMethodsUsed());
	        }
	        if (supplierModel.getCustomerDocumentsControl() != null) {
	            updateExisting.setCustomerDocumentsControl(supplierModel.getCustomerDocumentsControl());
	        }
	        if (supplierModel.getRevisionChangeHandling() != null) {
	            updateExisting.setRevisionChangeHandling(supplierModel.getRevisionChangeHandling());
	        }
	        if (supplierModel.getSupplierQualityEvaluations() != null) {
	            updateExisting.setSupplierQualityEvaluations(supplierModel.getSupplierQualityEvaluations());
	        }
	        if (supplierModel.getApprovedSupplierList() != null) {
	            updateExisting.setApprovedSupplierList(supplierModel.getApprovedSupplierList());
	        }
	        if (supplierModel.getCertifiedTestReports() != null) {
	            updateExisting.setCertifiedTestReports(supplierModel.getCertifiedTestReports());
	        }
	        if (supplierModel.getSupplierOnTimeDelivery() != null) {
	            updateExisting.setSupplierOnTimeDelivery(supplierModel.getSupplierOnTimeDelivery());
	        }
	        if (supplierModel.getSupplierMarketPriceCompetence() != null) {
	            updateExisting.setSupplierMarketPriceCompetence(supplierModel.getSupplierMarketPriceCompetence());
	        }
	        if (supplierModel.getAdequateAreaSafety() != null) {
	            updateExisting.setAdequateAreaSafety(supplierModel.getAdequateAreaSafety());
	        }
	        if (supplierModel.getHousekeepingProcedure() != null) {
	            updateExisting.setHousekeepingProcedure(supplierModel.getHousekeepingProcedure());
	        }

	        return supplierRepository.save(updateExisting);
	    }).orElse(null); 
	}
}

