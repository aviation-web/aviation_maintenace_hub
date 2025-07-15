package com.aeromaintenance.inspectionReport;

import com.aeromaintenance.storeAcceptance.StoreAcc;

import java.sql.Date;

public class StoreAccMapper {
    public static StoreAcc fromInspectionReport(InspectionReport dto) {
        StoreAcc storeAcc = new StoreAcc();
        storeAcc.setPartNum(dto.getPartNumber());
        storeAcc.setDescription(dto.getPartDesc());
        storeAcc.setBatch(dto.getBatchNumberObservation());
        storeAcc.setCondition(dto.getMaterialConditionObservation());
        storeAcc.setSupplier(dto.getSupplierName());

        if (dto.getDateOfManufacturingObservation() != null) {
            storeAcc.setDom(Date.valueOf(dto.getDateOfManufacturingObservation()));
        }

        if (dto.getSelfLifeObservation() != null) {
            storeAcc.setDoe(Date.valueOf(dto.getSelfLifeObservation()));
        }

        storeAcc.setQuantity(dto.getQty());

        if (dto.getDate() != null) {
            storeAcc.setDateOfRecipet(Date.valueOf(dto.getDate()));
        }

        storeAcc.setNameOfQualityInsp(dto.getCheckerUserName());
        storeAcc.setSignatureOfQualityInsp(dto.getCheckerUserId());

        return storeAcc;
    }
}
