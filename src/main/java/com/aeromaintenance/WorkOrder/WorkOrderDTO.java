package com.aeromaintenance.WorkOrder;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class WorkOrderDTO {
    private LocalDate issueDate;
    private String customerName;
    private String repairOrderNo;
    private String partNumber;
    private Integer qty;
    private String description;
    private String cmmRefNo;
    private String revNo;
    private LocalDate cmmRevDate;
    private String workshopManagerRemarks;
    private String issuedBy;
    private String certifyingStaffhours;
    private String technician;
    private String totalManHour;
    private String actionTaken;
    private String toolsUsed;
    private LocalDate qualityManagerSignDate;
    private LocalDate workshopManagerSignDate;
    private String snBn;
    private String srNumber; // customer_order  sr_no
    private String flag ="N";


    private Set<WorkOrderStepDTO> workOrderSteps;
    private Set<MaterialRequisitionDTO> materialRequisitions;

    public void setWorkOrderNo(String workOrderNo) {
    }

    @Data
    public static class WorkOrderStepDTO {
        private Integer stepNo;
        private String detailOfWorkDone;
        private String technicianSign;
        private String certifyingStaffSign;
    }

    @Data
    public static class MaterialRequisitionDTO {
        private Integer srNo;
        private String description;
        private String partNo;
        private String snbn;
        private Integer qty;
        private String remarks;
    }
}

