package com.aeromaintenance.MaterialReceiptNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aeromaintenance.PurchaseOrder.PurchaseOrder;
import com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO;
import com.aeromaintenance.exception.DuplicateRecordException;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialReceiptNoteService {
    
    @Autowired
    private MaterialReceiptNoteRepository mrnRepository;
    
    public MaterialReceiptNote createMRN(MaterialReceiptNote mrn) {
    	SecureRandom secureRandom = new SecureRandom();
	    long randomLong = Math.abs(secureRandom.nextLong());
        String uniquePart = String.valueOf(randomLong).substring(0, 8);
        String mrnNumber = "MRN_NO_" + uniquePart;
        mrn.setMrnNo(mrnNumber);
        String data[] = mrn.getPartNumber().split("\\|");
		String partNo = data[0].trim();
		Long id = Long.valueOf(data[1].trim());
        mrn.setPartNumber(partNo);
        MaterialReceiptNote  mrnObj = mrnRepository.save(mrn);
        int updateFlag = mrnRepository.updatePoPlaceFlag(partNo, id);
//        if (mrnRepository.existsByOrderNumberAndPartNumber(mrn.getOrderNumber(), mrn.getPartNumber())) {
//        	throw new DuplicateRecordException("PO number and PartNumber combination already exists");
//        }
        return mrnObj;
    }
    
    public List<MaterialReceiptNote> getAllMRNs() {
        return mrnRepository.findAll();
    }
    
    public Optional<MaterialReceiptNote> getMRNById(Long id) {
        return mrnRepository.findById(id);
    }
    
    public MaterialReceiptNote updateMRN(Long id, MaterialReceiptNote mrn) {
        if (!mrnRepository.existsById(id)) {
            throw new RuntimeException("MRN not found with id: " + id);
        }
        mrn.setMaterialId(id);
        return mrnRepository.save(mrn);
    }
    
    public void deleteMRN(Long id) {
        if (!mrnRepository.existsById(id)) {
            throw new RuntimeException("MRN not found with id: " + id);
        }
        mrnRepository.deleteById(id);
    }

	
}
