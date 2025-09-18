package com.aeromaintenance.MaterialReceiptNote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aeromaintenance.PurchaseOrder.PurchaseOrder;
import com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO;

public interface MaterialReceiptNoteRepository extends JpaRepository<MaterialReceiptNote, Long> {
    boolean existsByMrnNo(String mrnNo);
    
	boolean existsByOrderNumberAndPartNumber(String orderNumber, String partNumber);

    
}