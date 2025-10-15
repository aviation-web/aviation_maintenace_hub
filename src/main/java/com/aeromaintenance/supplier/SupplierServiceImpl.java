package com.aeromaintenance.supplier;

import java.util.List;

import com.common.SupplierNamePaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
    private SupplierRepository supplierRepository;
	
	 @Autowired
	    private SupplierRepositoryImpl supplierRepositoryImpl;
	
	@Override
	public SupplierModel saveSupplier(SupplierModel supplierModel) {
		 return supplierRepository.save(supplierModel);
	}
	
	/*
	 * public List<SupplierModel> getSuppliersWithUserActionMAndRoleQM() { return
	 * supplierRepository.findByUserActionAndUserRole("M", "QM"); }
	 */
	
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
            if (supplierModel.getPaymentTerms() != null) {
                updateExisting.setPaymentTerms(supplierModel.getPaymentTerms());
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
	        if (supplierModel.getUserAction() != null) {
	            updateExisting.setUserAction(supplierModel.getUserAction());
	        }
	        if (supplierModel.getRemark() != null) {
	            updateExisting.setRemark(supplierModel.getRemark());
	        }
			if (supplierModel.getCountryCode() != null) {
				updateExisting.setCountryCode(supplierModel.getCountryCode());
			}
			if (supplierModel.getQualityManagerCountryCode() != null) {
				updateExisting.setQualityManagerCountryCode(supplierModel.getQualityManagerCountryCode());
			}
			if (supplierModel.getSaleRepresentativeCountryCode() != null) {
				updateExisting.setSaleRepresentativeCountryCode(supplierModel.getSaleRepresentativeCountryCode());
			}
			if (supplierModel.getIsoCertificate() != null) {
				updateExisting.setIsoCertificate(supplierModel.getIsoCertificate());
			}

	        return supplierRepository.save(updateExisting);
	    }).orElse(null); 
	}

	@Override
	public List<SupplierModel> getAllPendingSupplierList(String userRole, String userAction) {
	    return supplierRepository.findByUserRoleAndUserAction(userRole, userAction);
	}

	@Override
	public List<SupplierModel> getAllEditingSupplierList(String userRole, String userAction) {
		 return supplierRepository.findByUserRoleAndUserAction(userRole, userAction);
	}
//	public int approveSupplier(SupplierDto supplierDto) {
//	    try {
//	        int result = 0;
//	        
//	        if (supplierDto.getAction().equals("S")) {
//	            // Print supplierId for debugging
//	            System.out.println("Checking supplierId: " + supplierDto.getSupplierId());
//	            
//	            // Check if supplier already exists
//	            boolean isExists = supplierRepositoryImpl.existsBySupplierId(supplierDto.getSupplierId());
//	            
//	            if (isExists) {
//	                System.out.println("Data is already in the database for supplierId: " + supplierDto.getSupplierId());
//	                return 0; // No insertion as supplier already exists
//	            } else {
//	                result = supplierRepositoryImpl.insertSupplierForm(supplierDto);
//	            }
//	        } else if (supplierDto.getAction().equals("E")) {
//	            // maker table update supplier_Reg action="M" change "E"
//	        }
//	        
//	        return result;
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    return 0;
//	}

	@Override
	public int approveSupplier(SupplierDto supplierDto) {
		 try {
			 int result=0;
			 int count=0;
			 if(supplierDto.getUserAction().equalsIgnoreCase("2")) {
				 System.out.println(supplierDto.getSupplierId());
				 if(!supplierRepositoryImpl.existsBySupplierId(supplierDto.getSupplierId())) {
					 result = supplierRepositoryImpl.insertSupplierForm(supplierDto);
					  if(result>0) {
						 count=supplierRepositoryImpl.updateSupplierTemp(supplierDto.getUserAction(), supplierDto.getSupplierId());
						 supplierDto.setRemark(supplierDto.getRemark());
						 }
				 }else {
					 //duplicate records
					 System.out.println("Allredy SuppilerId present in the database");	
				return -1;	 
				 }
			}else if(supplierDto.getUserAction().equals("3")) {
				
				result =supplierRepositoryImpl.updateEditSupplierTemp(supplierDto.getUserAction(), supplierDto.getSupplierId());
				 supplierDto.setRemark(supplierDto.getRemark());
			}
			 return result;
			 
		 }catch(Exception e) {
			 e.printStackTrace();	 }
		 
		 return 0;
	 }

	public List<SupplierNamePaymentDTO> findSupplierNamesAndPaymentTerms() {
		return supplierRepository.findSupplierNamesAndPaymentTerms();
	}
	
	public List<String> getAllSupplierNames() {
		return supplierRepository.findAllSupplierNames();
	}
}

