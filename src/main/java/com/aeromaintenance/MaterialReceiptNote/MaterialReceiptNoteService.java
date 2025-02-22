package com.aeromaintenance.MaterialReceiptNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialReceiptNoteService {
    
    @Autowired
    private MaterialReceiptNoteRepository mrnRepository;
    
    public MaterialReceiptNote createMRN(MaterialReceiptNote mrn) {
        if (mrnRepository.existsByMrnNo(mrn.getMrnNo())) {
            throw new RuntimeException("MRN number already exists");
        }
        return mrnRepository.save(mrn);
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
        mrn.setId(id);
        return mrnRepository.save(mrn);
    }
    
    public void deleteMRN(Long id) {
        if (!mrnRepository.existsById(id)) {
            throw new RuntimeException("MRN not found with id: " + id);
        }
        mrnRepository.deleteById(id);
    }
}
