package com.aeromaintenance.customerOrder;

import java.util.List;

import com.aeromaintenance.inspectionReport.InspectionReport;
import com.aeromaintenance.inspectionReport.InspectionReportDto;

public interface CustomerOrderService {

	List<String> saveInBatches(List<CustomerOrder> entities, int size);

	int approveReport(CustomerOrderDto order);
	
	public List<CustomerOrderDto> getAllViewOrderList();

	CustomerOrder updateOrder(Long id, CustomerOrder updateOrder);


}
