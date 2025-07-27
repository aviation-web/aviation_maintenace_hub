package com.aeromaintenance.WorkOrder;

import com.aeromaintenance.customerOrder.CustomerOrderHistoryDTO;
import com.aeromaintenance.customerOrder.CustomerOrderRepositoryCustom;
import com.aeromaintenance.customerOrder.CustomerOrderShortDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOrderRepository repository;

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    private CustomerOrderRepositoryCustom customerOrderRepositoryCustom;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public WorkOrder createWorkOrder(WorkOrderDTO dto) {
        WorkOrder entity = mapToEntity(dto);

        //  Custom formatted work order number like "AMC0001"
        entity.setWorkOrderNo(generateNextWorkOrderNo());
        WorkOrder savedOrder = repository.save(entity);

        // Update workorder flag = 1 in customer_order_history
        if (savedOrder.getRepairOrderNo() != null) {
            entityManager.createNativeQuery(
                            "UPDATE customer_order_history SET workorder = 1 WHERE order_no = :orderNo")
                    .setParameter("orderNo", savedOrder.getRepairOrderNo())
                    .executeUpdate();
        }

        return savedOrder;
    }

    @Override
    public List<WorkOrder> getAllWorkOrders() {
        return repository.findAll();
    }

    @Override
    public WorkOrder getWorkOrderByNo(String workOrderNo) {
        return repository.findByWorkOrderNo(workOrderNo)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found"));
    }


    @Override
    public WorkOrder updateWorkOrder(String workOrderNo, WorkOrderDTO dto) {
        WorkOrder existing = repository.findByWorkOrderNo(workOrderNo)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found"));

        // Update simple fields
        existing.setIssueDate(dto.getIssueDate());
        existing.setCustomerName(dto.getCustomerName());
        existing.setRepairOrderNo(dto.getRepairOrderNo());
        existing.setPartNumber(dto.getPartNumber());
        existing.setQty(dto.getQty());
        existing.setDescription(dto.getDescription());
        existing.setCmmRefNo(dto.getCmmRefNo());
        existing.setRevNo(dto.getRevNo());
        existing.setWorkshopManagerRemarks(dto.getWorkshopManagerRemarks());
        existing.setIssuedBy(dto.getIssuedBy());
        existing.setCertifyingStaff(dto.getCertifyingStaff());
        existing.setTechnician(dto.getTechnician());
        existing.setTotalManHour(dto.getTotalManHour());
        existing.setActionTaken(dto.getActionTaken());
        existing.setToolsUsed(dto.getToolsUsed());
        existing.setQualityManagerSignDate(dto.getQualityManagerSignDate());
        existing.setWorkshopManagerSignDate(dto.getWorkshopManagerSignDate());
        existing.setSnBn(dto.getSnBn());

        // Safely replace WorkOrderSteps
        if (dto.getWorkOrderSteps() != null) {
            existing.getWorkOrderSteps().clear(); // Clear existing
            List<WorkOrderStep> updatedSteps = dto.getWorkOrderSteps().stream().map(stepDTO -> {
                WorkOrderStep step = new WorkOrderStep();
                step.setStepNo(stepDTO.getStepNo());
                step.setDetailOfWorkDone(stepDTO.getDetailOfWorkDone());
                step.setTechnicianSign(stepDTO.getTechnicianSign());
                step.setCertifyingStaffSign(stepDTO.getCertifyingStaffSign());
                step.setWorkOrder(existing);
                return step;
            }).collect(Collectors.toList());
            existing.getWorkOrderSteps().addAll(updatedSteps);
        }

        // Safely replace MaterialRequisitions
        if (dto.getMaterialRequisitions() != null) {
            existing.getMaterialRequisitions().clear(); // Clear existing
            List<MaterialRequisitionNew> updatedRequisitions = dto.getMaterialRequisitions().stream().map(mrDTO -> {
                MaterialRequisitionNew mr = new MaterialRequisitionNew();
                mr.setSrNo(mrDTO.getSrNo());
                mr.setDescription(mrDTO.getDescription());
                mr.setPartNo(mrDTO.getPartNo());
                mr.setSnbn(mrDTO.getSnbn());
                mr.setQty(mrDTO.getQty());
                mr.setRemarks(mrDTO.getRemarks());
                mr.setWorkOrder(existing);
                return mr;
            }).collect(Collectors.toList());
            existing.getMaterialRequisitions().addAll(updatedRequisitions);
        }

        return repository.save(existing);
    }


    @Override
    public void deleteWorkOrder(String workOrderNo) {
        WorkOrder existing = repository.findByWorkOrderNo(workOrderNo)
                .orElseThrow(() -> new RuntimeException("WorkOrder not found"));
        repository.delete(existing);
    }

    private WorkOrder mapToEntity(WorkOrderDTO dto) {
        WorkOrder entity = new WorkOrder();

        entity.setIssueDate(dto.getIssueDate());
        entity.setCustomerName(dto.getCustomerName());
        entity.setRepairOrderNo(dto.getRepairOrderNo());
        entity.setPartNumber(dto.getPartNumber());
        entity.setQty(dto.getQty());
        entity.setDescription(dto.getDescription());
        entity.setCmmRefNo(dto.getCmmRefNo());
        entity.setRevNo(dto.getRevNo());
        entity.setWorkshopManagerRemarks(dto.getWorkshopManagerRemarks());
        entity.setIssuedBy(dto.getIssuedBy());
        entity.setCertifyingStaff(dto.getCertifyingStaff());
        entity.setTechnician(dto.getTechnician());
        entity.setTotalManHour(dto.getTotalManHour());
        entity.setActionTaken(dto.getActionTaken());
        entity.setToolsUsed(dto.getToolsUsed());
        entity.setQualityManagerSignDate(dto.getQualityManagerSignDate());
        entity.setWorkshopManagerSignDate(dto.getWorkshopManagerSignDate());
        entity.setSnBn(dto.getSnBn());

        if (dto.getWorkOrderSteps() != null) {
            entity.setWorkOrderSteps(dto.getWorkOrderSteps().stream().map(stepDTO -> {
                WorkOrderStep step = new WorkOrderStep();
                step.setStepNo(stepDTO.getStepNo()); // optional, if you're updating
                step.setDetailOfWorkDone(stepDTO.getDetailOfWorkDone());
                step.setTechnicianSign(stepDTO.getTechnicianSign());
                step.setCertifyingStaffSign(stepDTO.getCertifyingStaffSign());
                step.setWorkOrder(entity);
                return step;
            }).collect(Collectors.toList()));
        }

        if (dto.getMaterialRequisitions() != null) {
            entity.setMaterialRequisitions(dto.getMaterialRequisitions().stream().map(mrDTO -> {
                MaterialRequisitionNew mr = new MaterialRequisitionNew();
                mr.setSrNo(mrDTO.getSrNo()); // optional, if you're updating
                mr.setDescription(mrDTO.getDescription());
                mr.setPartNo(mrDTO.getPartNo());
                mr.setSnbn(mrDTO.getSnbn());
                mr.setQty(mrDTO.getQty());
                mr.setRemarks(mrDTO.getRemarks());
                mr.setWorkOrder(entity);
                return mr;
            }).collect(Collectors.toList()));
        }


        return entity;
    }
    // Fetch Pending Customer Orders (workorder = 0)
    public List<CustomerOrderHistoryDTO> getPendingWorkOrderHistory() {
        return customerOrderRepositoryCustom.findAllHistoryWithWorkOrderZero();
    }

    public CustomerOrderShortDTO getShortOrderBySrNo(String srNo) {
        return customerOrderRepositoryCustom.findOrderShortBySrNo(srNo);
    }


    private String generateNextWorkOrderNo() {
        // Find last workOrderNo with "AMC" prefix
        WorkOrder lastOrder = repository.findTopByWorkOrderNoStartingWithOrderByWorkOrderNoDesc("AMC").orElse(null);

        int nextNumber = 1;
        if (lastOrder != null) {
            String lastNo = lastOrder.getWorkOrderNo(); // e.g. "AMC0045"
            String numericPart = lastNo.substring(3);   // "0045"
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException e) {
                nextNumber = 1; // fallback in case of parse error
            }
        }

        return String.format("AMC%04d", nextNumber); // e.g., AMC0046
    }

}
