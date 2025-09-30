package com.aeromaintenance.MaterialReceiptNote;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MaterialReceiptNoteRepository extends JpaRepository<MaterialReceiptNote, Long> {
    boolean existsByMrnNo(String mrnNo);
    
	boolean existsByOrderNumberAndPartNumber(String orderNumber, String partNumber);

	@Transactional
	@Modifying
	@Query("UPDATE PurchaseOrder i SET i.orderPlacedFlag = 1 WHERE i.partNumber = :partNumber AND i.id = :id")
    int updatePoPlaceFlag(@Param("partNumber")String partNumber, @Param("id")Long id);

	List<MaterialReceiptNote> findByStatus(String status);

    
}