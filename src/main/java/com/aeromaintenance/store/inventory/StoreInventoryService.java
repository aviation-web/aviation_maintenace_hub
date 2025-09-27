package com.aeromaintenance.store.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StoreInventoryService {
	
	@Autowired
	public StoreInventoryRepo inventoryRepository;
	
	public List<StoreInventoryProjection> getAllInventory() {
        return inventoryRepository.getInventoryWithLocation();
    }

}


