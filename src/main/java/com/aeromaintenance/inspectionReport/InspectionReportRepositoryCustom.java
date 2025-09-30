package com.aeromaintenance.inspectionReport;

public interface InspectionReportRepositoryCustom {
	public int insertReportForm(InspectionReportDto form) ;
	public boolean existsByReportId(Long reportId);
	public int updateReportTemp(String userAction, Long inspectionReportId);
	public int updateEditReportTemp(String userAction, Long inspectionReportId);
	public boolean checkPartNoIsPresentInStore(String partNumber);
	public int getCurrentStokeFromInventory(String partNumber);
	public int UpdateCurrentQuantity(String partNumber, int currentStoke);
	public int insertInStoreInventory(InspectionReport reports);
	public int getRequiredQtyFromPurchaseOrder(String purchaseOrderNo, String partNumber);
	public int updatePoStatus(String status, String poNumber, String partNumber, int requiredQty);
	public int updateInHistoryTable(InspectionReportDto reportDto);

}
