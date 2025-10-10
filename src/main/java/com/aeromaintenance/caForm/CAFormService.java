package com.aeromaintenance.caForm;

public interface CAFormService {

	CAForm updateReport(String id, CAForm updateReport);

	void updateWorkOrderStatus(CAForm caForm, String status);

	void updateCustomerOrderStatus(CAForm caForm);

}
