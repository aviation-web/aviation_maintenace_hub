package com.aeromaintenance.inspectionReport;

public interface InspectionReportRepositoryCustom {
	public int insertReportForm(InspectionReportDto form) ;
	public boolean existsByReportId(Long reportId);
	public int updateReportTemp(String userAction, Long inspectionReportId);
	public int updateEditReportTemp(String userAction, Long inspectionReportId);

}
