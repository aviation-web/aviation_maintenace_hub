package com.aeromaintenance.WorkOrder;

import com.aeromaintenance.customerOrder.CustomerOrderHistoryDTO;
import com.aeromaintenance.customerOrder.CustomerOrderShortDTO;

import java.util.List;

public interface WorkOrderService {
    String createWorkOrder(WorkOrderDTO dto);
    Object getWorkOrderByNo(String workOrderNo);
    List<WorkOrder> getAllWorkOrders();
    WorkOrder updateWorkOrder(String workOrderNo, WorkOrderDTO dto);
    void deleteWorkOrder(String workOrderNo);

    List<CustomerOrderHistoryDTO> getPendingWorkOrderHistory();
    CustomerOrderShortDTO getShortOrderBySrNo(String srNo);

    public List<WorkOrder> getAllWorkOrdersWithDetails();

    // Fetch Pending Customer Orders (workorder = 0)
//    public List<CustomerOrderHistoryDTO> getPendingWorkOrderHistory() {
//        return customerOrderRepositoryCustom.findAllHistoryWithWorkOrderZero();
//    }

}