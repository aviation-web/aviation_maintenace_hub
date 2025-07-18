package com.aeromaintenance.MaterialRequisition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialRequisitionServiceImpl implements MaterialRequisitionService {

    @Autowired
    private MaterialRequisitionRepository repository;

    @Override
    public MaterialRequisitionDTO addMaterialRequisition(MaterialRequisitionDTO dto) {
        MaterialRequisition entity = convertToEntity(dto);
        entity = repository.save(entity);
        return convertToDTO(entity);
    }

    @Override
    public MaterialRequisitionDTO updateMaterialRequisition(Long id, MaterialRequisitionDTO dto) {
        Optional<MaterialRequisition> existing = repository.findById(id);
        if (existing.isPresent()) {
            MaterialRequisition entity = existing.get();
            entity.setWorkOrderNo(dto.getWorkOrderNo());
            entity.setDate(dto.getDate());
            entity.setPartNumber(dto.getPartNumber());
            entity.setDescription(dto.getDescription());
            entity.setRequestedQty(dto.getRequestedQty());
            entity.setIssuedQty(dto.getIssuedQty());
            entity.setBatchLotNo(dto.getBatchLotNo());
            repository.save(entity);
            return convertToDTO(entity);
        }
        throw new RuntimeException("Material Requisition not found with id: " + id);
    }

    @Override
    public void deleteMaterialRequisition(Long id) {
        repository.deleteById(id);
    }

    @Override
    public MaterialRequisitionDTO getMaterialRequisitionById(Long id) {
        MaterialRequisition entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material Requisition not found with id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public List<MaterialRequisitionDTO> getAllMaterialRequisitions() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private MaterialRequisitionDTO convertToDTO(MaterialRequisition entity) {
        return new MaterialRequisitionDTO(
                entity.getId(),
                entity.getMaterialRequisitionNo(),
                entity.getWorkOrderNo(),
                entity.getDate(),
                entity.getPartNumber(),
                entity.getDescription(),
                entity.getRequestedQty(),
                entity.getIssuedQty(),
                entity.getBatchLotNo(),
                entity.getUnitOfMeasurement()
        );
    }

    private MaterialRequisition convertToEntity(MaterialRequisitionDTO dto) {
        return new MaterialRequisition(
                dto.getId(),
                dto.getMaterialRequisitionNo(),
                dto.getWorkOrderNo(),
                dto.getDate(),
                dto.getPartNumber(),
                dto.getDescription(),
                dto.getRequestedQty(),
                dto.getIssuedQty(),
                dto.getBatchLotNo(),
                dto.getUnitOfMeasurement()
        );
    }
}

