package com.aeromaintenance.store.inventory;

import java.util.List;

import com.common.ProductDTO;
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
	@Query(value = """
    INSERT INTO store_inventory (part_number, part_description, quantity, rack_no, updated_by, updated_date)
    VALUES (:partNumber, :description, :quantity, :rackNo, :updatedBy, :updatedDate)
    ON DUPLICATE KEY UPDATE
        quantity = :quantity,
        rack_no = CONCAT_WS(',', rack_no,
            (SELECT GROUP_CONCAT(new_rack) FROM
                (SELECT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(:rackNo, ',', numbers.n), ',', -1)) AS new_rack
                 FROM
                    (SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 
                     UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8
                     UNION ALL SELECT 9 UNION ALL SELECT 10) numbers
                 WHERE n <= 1 + LENGTH(:rackNo) - LENGTH(REPLACE(:rackNo, ',', ''))
                ) racks
             WHERE FIND_IN_SET(new_rack, rack_no) = 0
            )
        ),
        updated_by = :updatedBy,
        updated_date = :updatedDate
    """, nativeQuery = true)
	int upsertInventory(
			@Param("partNumber") String partNumber,
			@Param("description") String description,
			@Param("quantity") int quantity,
			@Param("rackNo") String rackNo,
			@Param("updatedBy") String updatedBy,
			@Param("updatedDate") String updatedDate
	);

    @Query(value = """
    SELECT 
        pm.product_name AS productName,
        pm.product_description AS productDescription,
        pm.alternate_product1 AS alternateProduct1,
        pm.alternate_product2 AS alternateProduct2,
        pm.unit_of_measurement AS unitOfMeasurement,
        pm.mapping_type AS mappingType,
        COALESCE(si.quantity, 0) AS quantity,
        COALESCE(si1.quantity, 0) AS alternateQuantity1,
        COALESCE(si2.quantity, 0) AS alternateQuantity2
    FROM product_registration pm
    LEFT JOIN store_inventory si ON pm.product_name = si.part_number
    LEFT JOIN store_inventory si1 ON pm.alternate_product1 = si1.part_number
    LEFT JOIN store_inventory si2 ON pm.alternate_product2 = si2.part_number
""", nativeQuery = true)
    List<Object[]> getInventoryWithLocation1();





}

