package com.aeromaintenance.PurchaseRequisition;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseRequisitionService {
    @Autowired
    private PurchaseRequisitionRepository repository;

    // Method to get Purchase Requisition by ID
    public PurchaseRequisitionDTO getPurchaseRequisitionById(Long id) {
        PurchaseRequisition entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    // Create new Purchase Requisition
    @Transactional
    public PurchaseRequisitionDTO createPurchaseRequisition(PurchaseRequisitionDTO dto) {
        PurchaseRequisition entity = new PurchaseRequisition();
        BeanUtils.copyProperties(dto, entity);

        PurchaseRequisition savedEntity = repository.save(entity);

        PurchaseRequisitionDTO savedDto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(savedEntity, savedDto);

        return savedDto;
    }
    
 // View Purchase Requisition by ID
    public PurchaseRequisitionDTO viewPurchaseRequisitionById(Long id) {
        // Find the entity
        PurchaseRequisition entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        // Convert to DTO
        PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


    // Get All Purchase Requisitions
    public List<PurchaseRequisitionDTO> getAllPurchaseRequisitions() {
        return repository.findAll().stream()
            .map(entity -> {
                PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    // Update Purchase Requisition
    @Transactional
    public PurchaseRequisitionDTO updatePurchaseRequisition(Long id, PurchaseRequisitionDTO dto) {
        // Find the existing entity
        PurchaseRequisition existingEntity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        // Explicitly set the ID to prevent null ID issue
        dto.setId(id);

        // Copy properties, ignoring null values
        BeanUtils.copyProperties(dto, existingEntity, getNullPropertyNames(dto));

        // Save the updated entity
        PurchaseRequisition updatedEntity = repository.save(existingEntity);

        // Convert back to DTO
        PurchaseRequisitionDTO updatedDto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(updatedEntity, updatedDto);

        return updatedDto;
    }

    // Delete Purchase Requisition
    @Transactional
    public void deletePurchaseRequisition(Long id) {
        // Check if entity exists before deleting
        if (!repository.existsById(id)) {
            throw new RuntimeException("Purchase Requisition not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // Utility method to get null property names
    private String[] getNullPropertyNames(Object source) {
        final org.springframework.beans.BeanWrapper src = new org.springframework.beans.BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}