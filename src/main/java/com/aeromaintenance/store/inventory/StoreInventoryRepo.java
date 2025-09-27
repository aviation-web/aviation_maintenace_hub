package com.aeromaintenance.store.inventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeromaintenance.product.Product;

import javax.transaction.Transactional;

@Repository
public interface StoreInventoryRepo extends JpaRepository <Product, Long>{
	
	//@Procedure(procedureName = "get_store_inventory_with_location")
    @Query(value = "CALL fetch_store_inventory_with_location()", nativeQuery = true)
	List<StoreInventoryProjection> getInventoryWithLocation();


	@Modifying
	@Transactional
	@Query(value = "INSERT INTO store_inventory (part_number, part_description, quantity, rack_no, updated_by, updated_date) " +
			"VALUES (:partNumber, :description, :quantity, :rackNo, :updatedBy, :updatedDate) " +
			"ON DUPLICATE KEY UPDATE " +
			"quantity = quantity + :quantity, " +
			"rack_no = IF(rack_no IS NULL OR rack_no = '', :rackNo, " +
			"    IF(CONCAT(',', rack_no, ',') LIKE CONCAT('%,', :rackNo, ',%'), rack_no, CONCAT(rack_no, ',', :rackNo))" +
			"), " +
			"updated_by = :updatedBy, updated_date = :updatedDate",
			nativeQuery = true)
	int upsertInventory(@Param("partNumber") String partNumber,
						@Param("description") String description,
						@Param("quantity") int quantity,
						@Param("rackNo") String rackNo,
						@Param("updatedBy") String updatedBy,
						@Param("updatedDate") String updatedDate);




}

