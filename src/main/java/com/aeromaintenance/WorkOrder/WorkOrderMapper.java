package com.aeromaintenance.WorkOrder;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WorkOrderMapper {

    public WorkOrderDTO toDto(WorkOrder entity1) {
        WorkOrderDTO dto = new WorkOrderDTO();

        dto.setWorkOrderNo(entity1.getWorkOrderNo());
        dto.setIssueDate(entity1.getIssueDate());
        dto.setCustomerName(entity1.getCustomerName());
        dto.setRepairOrderNo(entity1.getRepairOrderNo());
        dto.setPartNumber(entity1.getPartNumber());
        dto.setQty(entity1.getQty());
        dto.setDescription(entity1.getDescription());
        dto.setCmmRefNo(entity1.getCmmRefNo());
        dto.setRevNo(entity1.getRevNo());
        dto.setWorkshopManagerRemarks(entity1.getWorkshopManagerRemarks());
        dto.setIssuedBy(entity1.getIssuedBy());
        dto.setCertifyingStaff(entity1.getCertifyingStaff());
        dto.setTechnician(entity1.getTechnician());
        dto.setTotalManHour(entity1.getTotalManHour());
        dto.setActionTaken(entity1.getActionTaken());
        dto.setToolsUsed(entity1.getToolsUsed());
        dto.setQualityManagerSignDate(entity1.getQualityManagerSignDate());
        dto.setWorkshopManagerSignDate(entity1.getWorkshopManagerSignDate());
        dto.setSnBn(entity1.getSnBn());

        // Steps
        if (entity1.getWorkOrderSteps() != null) {
            dto.setWorkOrderSteps(entity1.getWorkOrderSteps().stream().map(step -> {
                WorkOrderDTO.WorkOrderStepDTO s = new WorkOrderDTO.WorkOrderStepDTO();
                s.setStepNo(step.getStepNo());
                s.setDetailOfWorkDone(step.getDetailOfWorkDone());
                s.setTechnicianSign(step.getTechnicianSign());
                s.setCertifyingStaffSign(step.getCertifyingStaffSign());
                return s;
            }).collect(Collectors.toList()));
        }

        // Material Requisitions
        if (entity1.getMaterialRequisitions() != null) {
            dto.setMaterialRequisitions(entity1.getMaterialRequisitions().stream().map(mat -> {
                WorkOrderDTO.MaterialRequisitionDTO m = new WorkOrderDTO.MaterialRequisitionDTO();
                m.setSrNo(mat.getSrNo());
                m.setDescription(mat.getDescription());
                m.setPartNo(mat.getPartNo());
                m.setSnbn(mat.getSnbn());
                m.setQty(mat.getQty());
                m.setRemarks(mat.getRemarks());
                return m;
            }).collect(Collectors.toList()));
        }

        return dto;
    }
}
