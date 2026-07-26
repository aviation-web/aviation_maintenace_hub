package com.aeromaintenance.customerOrder;

import com.aeromaintenance.inspectionReport.InspectionReportDto;

import java.util.List;

public interface CustomerOrderRepositoryCustom {
	public int insertCustomerOrderHistory(CustomerOrderDto order) ;
	public boolean existsBySrNo(String srNo);
	public int updateCustomerOrderTemp(String userAction, String srNo);
	public int updateEditCustomerOrderTemp(String userAction, String srNo);
	//  New method added for fetching workOrder=0 records
	List<CustomerOrderHistoryDTO> findAllHistoryWithWorkOrderZero();


	CustomerOrderShortDTO findOrderShortBySrNo(String srNo);
}
