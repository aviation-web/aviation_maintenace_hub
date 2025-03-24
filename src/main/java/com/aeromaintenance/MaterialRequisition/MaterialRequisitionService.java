package com.aeromaintenance.MaterialRequisition;


import java.util.List;

public interface MaterialRequisitionService {
    MaterialRequisitionDTO addMaterialRequisition(MaterialRequisitionDTO materialRequisitionDTO);
    MaterialRequisitionDTO updateMaterialRequisition(Long id, MaterialRequisitionDTO materialRequisitionDTO);
    void deleteMaterialRequisition(Long id);
    MaterialRequisitionDTO getMaterialRequisitionById(Long id);
    List<MaterialRequisitionDTO> getAllMaterialRequisitions();
}
