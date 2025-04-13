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
        Optional<StoreAcc> existingData = repository.findById(id);
        if (existingData.isPresent()) {
            ///storeAcceptance.setPartNum(id);
        	storeAcceptance.getId();
            StoreAcc updatedStoreAcc = repository.save(storeAcceptance);
            logger.debug("Store acceptance updated successfully with ID: {}", id);
            return updatedStoreAcc;
        }
        logger.warn("Store acceptance with ID {} not found, update failed", id);
        return null;
    }

    public void deleteStoreAcceptance(Long id) {
        logger.info("Deleting store acceptance with ID: {}", id);
        repository.deleteById(id);
        logger.debug("Store acceptance deleted successfully with ID: {}", id);
    }
}
