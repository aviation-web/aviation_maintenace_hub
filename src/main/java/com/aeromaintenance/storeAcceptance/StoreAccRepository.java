package com.aeromaintenance.storeAcceptance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreAccRepository extends JpaRepository<StoreAcc, Long> {
}
