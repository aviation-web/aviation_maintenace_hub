package com.aeromaintenance.storeAcceptance;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeromaintenance.inspectionReport.InspectionReport;

import java.util.Optional;
import java.util.List;


@Repository
public interface StoreAccRepository extends JpaRepository<StoreAcc, Long> {

	// fetch only records where flag = "N"
	List<StoreAcc> findByFlag(String flag);

	@Query("Select s.quantity from StoreAcc s where s.partNum = :partNum")
	int getReceiveQuantityByPartNO(@Param("partNum") String partNum);

	Optional<StoreAcc> findByInspectionReportId(Long inspectionReportId);


}
