package com.aeromaintenance.inspectionReport;

import com.aeromaintenance.storeAcceptance.StoreAcc;

import java.sql.Date;

public class StoreAccMapper {
    public static StoreAcc fromInspectionReport(InspectionReport dto) {
        StoreAcc storeAcc = new StoreAcc();
        storeAcc.setInspectionReportId(dto.getInspectionReportId());
        storeAcc.setPartNum(dto.getPartNumber());
        storeAcc.setDescription(dto.getPartDesc());
        storeAcc.setBatch(dto.getBatchNumberObservation());
        storeAcc.setCondition("New");
        storeAcc.setSupplier(dto.getSupplierName());
        storeAcc.setNameOfQualityInsp(dto.getMakerUserName());

        if (dto.getDateOfManufacturingObservation() != null) {
            storeAcc.setDom(Date.valueOf(dto.getDateOfManufacturingObservation()));
        }

        if (dto.getSelfLifeObservation() != null) {
            storeAcc.setDoe(Date.valueOf(dto.getDateOfExpiryObservation()));
        }

        storeAcc.setQuantity(dto.getQtyReceive());

        if (dto.getDate() != null) {
            storeAcc.setDateOfRecipet(Date.valueOf(dto.getDate()));
        }

        return storeAcc;
    }
}
