package com.aeromaintenance.store.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.aeromaintenance.product.Product;

@Repository
public interface StoreInventoryRepo extends JpaRepository <Product, Long>{
	
	//@Procedure(procedureName = "get_store_inventory_with_location")
    @Query(value = "CALL fetch_store_inventory_with_location()", nativeQuery = true)
	List<StoreInventoryProjection> getInventoryWithLocation();

}
