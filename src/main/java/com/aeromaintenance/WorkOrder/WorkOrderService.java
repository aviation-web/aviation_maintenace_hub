package com.aeromaintenance.WorkOrder;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderService {

    private final WorkOrderRepository repo;

    public WorkOrderService(WorkOrderRepository repo) {
        this.repo = repo;
    }

    public WorkOrder saveWorkOrder(WorkOrder workOrder) {
        return repo.save(workOrder);
    }

    public List<WorkOrder> getAllWorkOrders() {
        return repo.findAll();
    }

    public WorkOrder getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    public WorkOrder updateWorkOrder(WorkOrder workOrder) {
        if (workOrder.getId() != null && repo.existsById(workOrder.getId())) {
            WorkOrder existing = repo.findById(workOrder.getId()).orElse(null);
            if (existing != null) {
                // Clear existing embedded collections
                existing.getWorkSteps().clear();
                existing.getMaterials().clear();

                // Reassign fields
                existing.setRepairOrderNo(workOrder.getRepairOrderNo());
                existing.setIssueDate(workOrder.getIssueDate());
                existing.setOperatorName(workOrder.getOperatorName());
                existing.setSerialNumber(workOrder.getSerialNumber());
                existing.setBatchNumber(workOrder.getBatchNumber());
                existing.setQuantity(workOrder.getQuantity());
                existing.setDescription(workOrder.getDescription());
                existing.setCmmRef(workOrder.getCmmRef());
                existing.setRevNo(workOrder.getRevNo());
                existing.setWorkshopManagerRemarks(workOrder.getWorkshopManagerRemarks());

                existing.getWorkSteps().addAll(workOrder.getWorkSteps());  // re-add
                existing.getMaterials().addAll(workOrder.getMaterials());  // re-add

                existing.setCertifyingStaff(workOrder.getCertifyingStaff());
                existing.setCertifyingDate(workOrder.getCertifyingDate());
                existing.setTechnicianHours(workOrder.getTechnicianHours());
                existing.setCertifyingStaffHours(workOrder.getCertifyingStaffHours());
                existing.setTotalManHours(workOrder.getTotalManHours());
                existing.setDocumentsVerified(workOrder.isDocumentsVerified());
                existing.setFinalApprovalByWorkshopManager(workOrder.getFinalApprovalByWorkshopManager());
                existing.setFinalApprovalByQualityManager(workOrder.getFinalApprovalByQualityManager());
                existing.setFinalDate(workOrder.getFinalDate());

                return repo.save(existing);  // update
            }
        }

        // Save new if ID is null or doesn't exist
        return repo.save(workOrder);
    }

}
