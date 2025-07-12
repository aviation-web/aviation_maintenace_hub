package com.aeromaintenance.WorkOrder;

import com.aeromaintenance.customerOrder.CustomerOrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private CustomerOrderRepositoryCustom customerOrderRepositoryCustom;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional //  Needed for update query to run inside a transaction
    public WorkOrder saveWorkOrder(WorkOrder workOrder) {
        if (workOrder.getCustomerOrderSrNo() != null) {
            try {
                // Fetch full record from customer_order_history
                Object[] result = (Object[]) entityManager.createNativeQuery(
                        "SELECT part_no, part_desc, qty, remark FROM customer_order_history WHERE sr_no = :srNo")
                        .setParameter("srNo", workOrder.getCustomerOrderSrNo())
                        .getSingleResult();

                if (result != null) {
                    String partNo = (String) result[0];
                    String description = (String) result[1];
                    Integer quantity = result[2] != null ? Integer.parseInt(result[2].toString()) : 0;
                    String remark = (String) result[3];

                    // Set in WorkOrder
                    workOrder.setPartNumber(partNo);
                    workOrder.setDescription(description);
                    workOrder.setQuantity(quantity);

                    // Set in PartsUsed
                    PartsUsed partUsed = new PartsUsed();
                    partUsed.setPartNo(partNo);
                    partUsed.setDescription(description);
                    partUsed.setQuantity(quantity);
                    partUsed.setRemarks(remark);
                    partUsed.setWorkOrder(workOrder);

                    workOrder.getPartsUsed().add(partUsed);
                }

            } catch (Exception e) {
                throw new RuntimeException("Error while fetching data for SrNo: " + workOrder.getCustomerOrderSrNo(), e);
            }
        }

        // Link WorkDoneSteps (if any)
        if (workOrder.getWorkDoneSteps() != null) {
            for (WorkDoneStep step : workOrder.getWorkDoneSteps()) {
                step.setWorkOrder(workOrder);
            }
        }

        // Save the WorkOrder
        WorkOrder savedWorkOrder = workOrderRepository.save(workOrder);

        //  Update workorder flag = 1 in customer_order_history
        if (workOrder.getCustomerOrderSrNo() != null) {
            entityManager.createNativeQuery("UPDATE customer_order_history SET workorder = 1 WHERE sr_no = :srNo")
                    .setParameter("srNo", workOrder.getCustomerOrderSrNo())
                    .executeUpdate();
        }

        return savedWorkOrder;
    }

    //  Get All WorkOrders
    public List<WorkOrder> getAll() {
        return workOrderRepository.findAll();
    }

    //  Get WorkOrder by ID
    public WorkOrder getById(Long id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found with ID: " + id));
    }

    //  Update WorkOrder with all fields and nested lists
    public WorkOrder updateWorkOrder(Long id, WorkOrder updatedOrder) {
    WorkOrder existing = getById(id); // fetch existing

    //  Update scalar fields
    existing.setWorkOrderNo(updatedOrder.getWorkOrderNo());
    existing.setRepairOrder(updatedOrder.getRepairOrder());
    existing.setCustomerName(updatedOrder.getCustomerName());
    existing.setPartNumber(updatedOrder.getPartNumber());
    existing.setDescription(updatedOrder.getDescription());
    existing.setQuantity(updatedOrder.getQuantity());
    existing.setCmmRefNo(updatedOrder.getCmmRefNo());
    existing.setSerialNumber(updatedOrder.getSerialNumber());
    existing.setRevisionNo(updatedOrder.getRevisionNo());
    existing.setDate(updatedOrder.getDate());
    existing.setWorkshopManagerRemarks(updatedOrder.getWorkshopManagerRemarks());
    existing.setIssuedBy(updatedOrder.getIssuedBy());
    existing.setTools(updatedOrder.getTools());
    existing.setManHours(updatedOrder.getManHours());
    existing.setCertifyingStaff(updatedOrder.getCertifyingStaff());
    existing.setTechnician(updatedOrder.getTechnician());
    existing.setTotalManhour(updatedOrder.getTotalManhour());

    //  Clear and replace WorkDoneSteps
    existing.getWorkDoneSteps().clear();
    if (updatedOrder.getWorkDoneSteps() != null) {
        for (WorkDoneStep step : updatedOrder.getWorkDoneSteps()) {
            step.setWorkOrder(existing);
            existing.getWorkDoneSteps().add(step);
        }
    }

    //  Clear and replace PartsUsed
    existing.getPartsUsed().clear();
    if (updatedOrder.getPartsUsed() != null) {
        for (PartsUsed part : updatedOrder.getPartsUsed()) {
            part.setWorkOrder(existing);
            existing.getPartsUsed().add(part);
        }
    }

    //  Save updated entity
    return workOrderRepository.save(existing);
}

    // Delete WorkOrder
    public void deleteWorkOrder(Long id) {
        workOrderRepository.deleteById(id);
    }

    // Fetch Pending Customer Orders (workorder = 0)
    public List<Object[]> getPendingWorkOrderHistory() {
        return customerOrderRepositoryCustom.findAllHistoryWithWorkOrderZero();
    }
}
