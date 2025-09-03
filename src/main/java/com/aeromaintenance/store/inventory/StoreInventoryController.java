package com.aeromaintenance.store.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inventory")
public class StoreInventoryController {
	
	@Autowired
	private StoreInventoryService inventoryService;
	
	@GetMapping("/storeInventory")
    public List<StoreInventoryProjection> getAvailableProducts() {
        return inventoryService.getAllInventory();
    }

}
