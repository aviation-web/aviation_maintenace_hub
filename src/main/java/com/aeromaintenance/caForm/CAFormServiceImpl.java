package com.aeromaintenance.caForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CAFormServiceImpl implements CAFormService {
	
	@Autowired
	private CAFormRepository repository;
	

	@Override
	public CAForm updateReport(Long id, CAForm updateReport) {
		return repository.findById(id)
	            .map(existingReport -> {
	                // Update all necessary fields
	            	existingReport.setStatus(updateReport.getStatus());
	            	existingReport.setRemarks(updateReport.getRemarks());
	            	existingReport.setApproveDesign13a(updateReport.getApproveDesign13a());
	            	existingReport.setNonApproveDesign13a(updateReport.getNonApproveDesign13a());
	            	existingReport.setOtherRegulation14a(updateReport.getOtherRegulation14a());
	            	existingReport.setAuthorisedSign13b(updateReport.getAuthorisedSign13b());
	            	existingReport.setAuthorisationNumber13c(updateReport.getAuthorisationNumber13c());
	            	existingReport.setAuthorisedSign14b(updateReport.getAuthorisedSign14b());
	            	existingReport.setApprovalRefNo14c(updateReport.getApprovalRefNo14c());
	            	existingReport.setName13d(updateReport.getName13d());
	            	existingReport.setDate13e(updateReport.getDate13e());
	            	existingReport.setName14d(updateReport.getName14d());
	            	existingReport.setDate14e(updateReport.getDate14e());
	            	
	                return repository.save(existingReport);
	            })
	            .orElse(null);
	}

}
