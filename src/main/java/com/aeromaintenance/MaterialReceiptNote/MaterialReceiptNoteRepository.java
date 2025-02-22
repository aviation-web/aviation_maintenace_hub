package com.aeromaintenance.MaterialReceiptNote;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialReceiptNoteRepository extends JpaRepository<MaterialReceiptNote, Long> {
    boolean existsByMrnNo(String mrnNo);
}