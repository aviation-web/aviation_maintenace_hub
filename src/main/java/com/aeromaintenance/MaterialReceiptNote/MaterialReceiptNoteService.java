package com.aeromaintenance.MaterialReceiptNote;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aeromaintenance.PurchaseOrder.PurchaseOrder;
import com.aeromaintenance.PurchaseOrder.PurchaseOrderDTO;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO;
import com.aeromaintenance.exception.DuplicateRecordException;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialReceiptNoteService {
    
    @Autowired
    private MaterialReceiptNoteRepository mrnRepository;
    
    public MaterialReceiptNote createMRN(MaterialReceiptNote mrn) {
    	// Get the current year's last two digits
        String yearSuffix = String.valueOf(java.time.Year.now().getValue()).substring(2);

        // Fetch the latest MRN number from DB (descending order)
        List<String> mrns = mrnRepository.findLatestMrnNo();
        String lastMrn = mrns.isEmpty() ? null : mrns.get(0);


        int nextNumber = 1; // default starting number

        if (lastMrn != null && lastMrn.endsWith(yearSuffix)) {
            // Extract numeric portion (e.g., from MRN_00012525 -> 000125)
            String numericPart = lastMrn.substring(4, 10);
            try {
                nextNumber = Integer.parseInt(numericPart) + 1;
            } catch (NumberFormatException e) {
                nextNumber = 1;
            }
        }

        // Format the numeric portion with leading zeros
        String formattedNumber = String.format("%06d", nextNumber);

        // Create the new MRN number (e.g., MRN_00000225)
        String mrnNumber = "MRN_" + formattedNumber + yearSuffix;

        // Double-check if MRN already exists (just to be safe)
        while (mrnRepository.existsByMrnNo(mrnNumber)) {
            nextNumber++;
            formattedNumber = String.format("%06d", nextNumber);
            mrnNumber = "MRN_" + formattedNumber + yearSuffix;
        }

        // Assign MRN number
        mrn.setMrnNo(mrnNumber);

        // Split and process part number
        String[] data = mrn.getPartNumber().split("\\|");
        String partNo = data[0].trim();
        Long id = Long.valueOf(data[1].trim());
        mrn.setPartNumber(partNo);

        // Save MRN and update PO flag
        MaterialReceiptNote mrnObj = mrnRepository.save(mrn);
        int updateFlag = mrnRepository.updatePoPlaceFlag(partNo, id);

        return mrnObj;
    }
//    	SecureRandom secureRandom = new SecureRandom();
//	    long randomLong = Math.abs(secureRandom.nextLong());
//        String uniquePart = String.valueOf(randomLong).substring(0, 8);
//        String mrnNumber = "MRN_NO_" + uniquePart;
//        mrn.setMrnNo(mrnNumber);
//        String data[] = mrn.getPartNumber().split("\\|");
//		String partNo = data[0].trim();
//		Long id = Long.valueOf(data[1].trim());
//        mrn.setPartNumber(partNo);
//        MaterialReceiptNote  mrnObj = mrnRepository.save(mrn);
//        int updateFlag = mrnRepository.updatePoPlaceFlag(partNo, id);
////        if (mrnRepository.existsByOrderNumberAndPartNumber(mrn.getOrderNumber(), mrn.getPartNumber())) {
////        	throw new DuplicateRecordException("PO number and PartNumber combination already exists");
////        }
//        return mrnObj;
//    }
//    
    public List<MaterialReceiptNote> getAllMRNs() {
        return mrnRepository.findByStatus("Open");
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
