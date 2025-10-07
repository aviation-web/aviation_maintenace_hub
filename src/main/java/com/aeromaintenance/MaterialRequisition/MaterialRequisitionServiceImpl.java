package com.aeromaintenance.MaterialRequisition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialRequisitionServiceImpl implements MaterialRequisitionService {

    @Autowired
    private MaterialRequisitionRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MaterialRequisitionDTO addMaterialRequisition(MaterialRequisitionDTO dto) {
        // Fetch the last MR number
        String lastNo = repository.findLastMaterialRequisitionNo();

        // Generate next number
        String nextNo = generateNextMaterialRequisitionNo(lastNo);

        // Convert DTO to Entity
        MaterialRequisition entity = convertToEntity(dto);
        entity.setMaterialRequisitionNo(nextNo);
        entity.setStatus("Open");

        // Save
        entity = repository.save(entity);

        if (dto.getId() != null) {
            int updated = entityManager.createNativeQuery(
                            "UPDATE material_requisition SET status = 'IN-PROGRESS' WHERE id = :id")
                    .setParameter("id", dto.getId())
                    .executeUpdate();
            System.out.println("Rows updated in customer_order: " + updated);
        }

        return convertToDTO(entity);
    }

    private String generateNextMaterialRequisitionNo(String lastNo) {
        if (lastNo == null || lastNo.isEmpty()) {
            return "MRNO_0001";
        }

        // Extract number part
        int number = Integer.parseInt(lastNo.split("_")[1]);
        number++;

        // Return in same format MRNO_0001, MRNO_0002, ...
        return String.format("MRNO_%04d", number);
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
//            entity.setIssueQty(dto.getIssueQty());
            entity.setIssuedQty(dto.getIssuedQty());
//            entity.setBatchLotNo(dto.getBatchLotNo());
            entity.setSupplierName(dto.getSupplierName());
            entity.setCurDate(dto.getCurDate());
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
        return repository.findAllActive().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
//                entity.getIssueQty(),
                entity.getIssuedQty(),
//                entity.getBatchLotNo(),
                entity.getUnitOfMeasurement(),
                entity.getSupplierName(),
                entity.getCurDate(),
                entity.getStatus()
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
//                dto.getIssueQty(),
                dto.getIssuedQty(),
//                dto.getBatchLotNo(),
                dto.getUnitOfMeasurement(),
                dto.getSupplierName(),
                dto.getCurDate(),
                dto.getStatus()
        );
    }
}

