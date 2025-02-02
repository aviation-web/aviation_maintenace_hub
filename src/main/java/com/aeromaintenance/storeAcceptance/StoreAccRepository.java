package com.aeromaintenance.storeAcceptance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreAccRepository extends JpaRepository<StoreAcc, String> {

}
