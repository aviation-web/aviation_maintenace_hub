package com.aeromaintenance.customerOrder;

import com.aeromaintenance.inspectionReport.InspectionReportDto;

import java.util.List;

public interface CustomerOrderRepositoryCustom {
	public int insertCustomerOrderHistory(CustomerOrderDto order) ;
	public boolean existsBySrNo(Long srNo);
	public int updateCustomerOrderTemp(String userAction, Long srNo);
	public int updateEditCustomerOrderTemp(String userAction, Long srNo);
	//  New method added for fetching workOrder=0 records
	List<CustomerOrderHistoryDTO> findAllHistoryWithWorkOrderZero();
}
