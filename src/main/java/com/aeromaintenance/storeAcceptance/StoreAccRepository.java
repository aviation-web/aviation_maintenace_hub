package com.aeromaintenance.storeAcceptance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreAccRepository extends JpaRepository<StoreAcc, Long> {

	@Query("Select s.quantity from StoreAcc s where s.partNum = :partNum")
	int getReceiveQuantityByPartNO(@Param("partNum") String partNum);
}
