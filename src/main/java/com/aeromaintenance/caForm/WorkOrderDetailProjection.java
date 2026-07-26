package com.aeromaintenance.caForm;

public interface WorkOrderDetailProjection {
	String getWorkOrderNo();
    String getCustomerName();
    String getRepairOrderNo();
    String getFormTrackingNumber();
    String getDescription();
    String getPartNo();
    String getItem();
    Integer getQuantity();
    String getSerialNo();
    String getStatus();
    String getRemarks();
}
