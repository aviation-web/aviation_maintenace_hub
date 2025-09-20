package com.aeromaintenance.storeAcceptance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreAccService {

    private static final Logger logger = LoggerFactory.getLogger(StoreAccService.class);

    @Autowired
    private StoreAccRepository repository;

    public List<StoreAcc> getAllStoreAcceptances() {
        logger.info("Fetching all store acceptances");
        List<StoreAcc> storeAcceptances = repository.findAll();
        logger.debug("Total store acceptances retrieved: {}", storeAcceptances.size());
        return storeAcceptances;
    }

    public StoreAcc getStoreAcceptanceById(Long id) {
        logger.info("Fetching store acceptance with ID: {}", id);
        return repository.findById(id)
                .orElseGet(() -> {
                    logger.warn("Store acceptance with ID {} not found", id);
                    return null;
                });
    }

    public StoreAcc saveStoreAcceptance(StoreAcc storeAcceptance) {
        logger.info("Saving new store acceptance with partNum: {}", storeAcceptance.getPartNum());
        StoreAcc savedStoreAcc = repository.save(storeAcceptance);
        logger.debug("Store acceptance saved successfully with ID: {}", savedStoreAcc.getPartNum());
        return savedStoreAcc;
    }

    public StoreAcc updateStoreAcceptance(Long id, StoreAcc storeAcceptance) {
        logger.info("Updating store acceptance with ID: {}", id);
        return repository.findById(id)
                .map(existing -> {
                    // copy fields from request body to existing entity
                    existing.setPartNum(storeAcceptance.getPartNum());
                    existing.setDescription(storeAcceptance.getDescription());
                    existing.setBatch(storeAcceptance.getBatch());
                    existing.setCondition(storeAcceptance.getCondition());
                    existing.setSupplier(storeAcceptance.getSupplier());
                    existing.setDom(storeAcceptance.getDom());
                    existing.setDoe(storeAcceptance.getDoe());
                    existing.setQuantity(storeAcceptance.getQuantity());
                    existing.setDateOfRecipet(storeAcceptance.getDateOfRecipet());
                    existing.setNameOfQualityInsp(storeAcceptance.getNameOfQualityInsp());
                    existing.setSignatureOfQualityInsp(storeAcceptance.getSignatureOfQualityInsp());
                    existing.setRackNo(storeAcceptance.getRackNo());
                    existing.setUpdatedBy(storeAcceptance.getUpdatedBy());
                    existing.setUpdatedDate(String.valueOf(java.time.LocalDateTime.now())); // auto update timestamp

                    StoreAcc updatedStoreAcc = repository.save(existing);
                    logger.debug("Store acceptance updated successfully with ID: {}", id);
                    return updatedStoreAcc;
                })
                .orElseThrow(() -> new RuntimeException("Store acceptance with ID " + id + " not found"));
    }


    public void deleteStoreAcceptance(Long id) {
        logger.info("Deleting store acceptance with ID: {}", id);
        repository.deleteById(id);
        logger.debug("Store acceptance deleted successfully with ID: {}", id);
    }
}
