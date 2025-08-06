package com.aeromaintenance.caForm;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CAFormRepository extends JpaRepository<CAForm, Long>{

	@Query("Select m.workOrderNo from WorkOrder m")
	List<String> findAllWorkOrder();

	@Query("SELECT new com.aeromaintenance.caForm.workOrderDetailDto(m.description, m.partNumber, m.qty, m.snBn) " +
		       "FROM WorkOrder m WHERE m.workOrderNo = :workOrderNo")
	Optional<workOrderDetailDto> findDetailsByWorkOrder(@Param("workOrderNo") String workOrderNo);

}
