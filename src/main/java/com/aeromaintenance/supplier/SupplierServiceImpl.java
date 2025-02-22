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
	        if (supplierModel.getFaxNum() != null) {
	            updateExisting.setFaxNum(supplierModel.getFaxNum());
	        }
	        if (supplierModel.getEmail() != null) {
	            updateExisting.setEmail(supplierModel.getEmail());
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
	        if(supplierModel.getSaleRepresentativeName()!=null) {
	        	updateExisting.setSaleRepresentativeName(supplierModel.getSaleRepresentativeName());
	        }
	        
	        if (supplierModel.getSaleRepresentativePhoneNumber() != null) {
	            updateExisting.setSaleRepresentativePhoneNumber(supplierModel.getSaleRepresentativePhoneNumber());
	        }
	        if (supplierModel.getSaleRepresentativeEmailId() != null) {
	            updateExisting.setSaleRepresentativeEmailId(supplierModel.getSaleRepresentativeEmailId());
	        }
	        if (supplierModel.getCoreProcess() != null) {
	            updateExisting.setCoreProcess(supplierModel.getCoreProcess());
	        }
	        if (supplierModel.getWorkYear() != null) {
	            updateExisting.setWorkYear(supplierModel.getWorkYear());
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
	        if (supplierModel.getNumEmp() != null) {
	            updateExisting.setNumEmp(supplierModel.getNumEmp());
	        }
	        if (supplierModel.getNumOpeShift() != null) {
	            updateExisting.setNumOpeShift(supplierModel.getNumOpeShift());
	        }
	        if (supplierModel.getQuaManual() != null) {
	            updateExisting.setQuaManual(supplierModel.getQuaManual());
	        }
	        if (supplierModel.getTurnOver() != null) {
	            updateExisting.setTurnOver(supplierModel.getTurnOver());
	        }
	        if (supplierModel.getIndependenceManuf() != null) {
	        	 updateExisting.setIndependenceManuf(supplierModel.getIndependenceManuf());
	            //updateExisting.setIndependenceManuf(supplierModel.getIndependenceManuf());
	        }
	        if (supplierModel.getDocumentedOperative() != null) {
	            updateExisting.setDocumentedOperative(supplierModel.getDocumentedOperative());
	        }
	        if (supplierModel.getDocumentedProcedure() != null) {
	            updateExisting.setDocumentedProcedure(supplierModel.getDocumentedProcedure());
	        }
	        if (supplierModel.getProductShipment() != null) {
	            updateExisting.setProductShipment(supplierModel.getProductShipment());
	        }
	        if (supplierModel.getProcessDocumented() != null) {
	            updateExisting.setProcessDocumented(supplierModel.getProcessDocumented());
	        }
	        if (supplierModel.getSamplingIncomingInsp() != null) {
	            updateExisting.setSamplingIncomingInsp(supplierModel.getSamplingIncomingInsp());
	        }
	        if (supplierModel.getReceivingInspectionResultsOnFile() != null) {
	            updateExisting.setReceivingInspectionResultsOnFile(supplierModel.getReceivingInspectionResultsOnFile());
	        }
	        if (supplierModel.getIdentificationMaintained() != null) {
	            updateExisting.setIdentificationMaintained(supplierModel.getIdentificationMaintained());
	        }
	        if (supplierModel.getSepInsMaterial() != null) {
	            updateExisting.setSepInsMaterial(supplierModel.getSepInsMaterial());
	        }
	        if (supplierModel.getNonConMaterial() != null) {
	            updateExisting.setNonConMaterial(supplierModel.getNonConMaterial());
	        }
	        if (supplierModel.getAffectCusReq() != null) {
	            updateExisting.setAffectCusReq(supplierModel.getAffectCusReq());
	        }// incoming inspection end 
	        
	        if (supplierModel.getEquipCalibrated() != null) {
	            updateExisting.setEquipCalibrated(supplierModel.getEquipCalibrated());
	        }
	        if (supplierModel.getRecalibration() != null) {
	            updateExisting.setRecalibration(supplierModel.getRecalibration());
	        }
	        if (supplierModel.getScopeOfWork() != null) {
	            updateExisting.setScopeOfWork(supplierModel.getScopeOfWork());
	        }
	        if (supplierModel.getWrittenWorkInstructionsAvaibleInStation() != null) {
	            updateExisting.setWrittenWorkInstructionsAvaibleInStation(supplierModel.getWrittenWorkInstructionsAvaibleInStation());
	        }
	        if (supplierModel.getFinalInspectionEvidence() != null) {
	            updateExisting.setFinalInspectionEvidence(supplierModel.getFinalInspectionEvidence());
	        }
	        if (supplierModel.getStatisMethod() != null) {
	            updateExisting.setStatisMethod(supplierModel.getStatisMethod());
	        }
	        if (supplierModel.getSuppliedDocument() != null) {
	            updateExisting.setSuppliedDocument(supplierModel.getSuppliedDocument());
	        }
	        if (supplierModel.getIncludeMethod()!= null) {
	            updateExisting.setIncludeMethod(supplierModel.getIncludeMethod());
	        }
	        if (supplierModel.getQualityCapabilities() != null) {
	            updateExisting.setQualityCapabilities(supplierModel.getQualityCapabilities());
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
	        if (supplierModel.getMarketPrice() != null) {
	            updateExisting.setMarketPrice(supplierModel.getMarketPrice());
	        }
	        if (supplierModel.getSafetyProgram() != null) {
	            updateExisting.setSafetyProgram(supplierModel.getSafetyProgram());
	        }
	        if (supplierModel.getHouseKeeping() != null) {
	            updateExisting.setHouseKeeping(supplierModel.getHouseKeeping());
	        }

	        return supplierRepository.save(updateExisting);
	    }).orElse(null); 
	}
}

