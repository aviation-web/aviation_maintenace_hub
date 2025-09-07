package com.aeromaintenance.inspectionReport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionReportRepository extends JpaRepository<InspectionReport, Long> {

	@Query("Select m.partNumber from MaterialReceiptNote m")
	List<String> findAllPartNumber();
	
	@Query("SELECT new com.aeromaintenance.inspectionReport.PartDetailsDTO(m.mrnNo, m.supplierName, m.orderNumber, m.receiptDate, m.partDescription, m.quantity) " +
		       "FROM MaterialReceiptNote m WHERE m.partNumber = :partNumber")
	Optional<PartDetailsDTO>findDetailsByPartNumber(@Param("partNumber") String partNumber);

	@Query("SELECT i FROM InspectionReport i WHERE i.userAction = '1' AND makerUserName <> :makerUserName")
	List<InspectionReport> getAllPendingList(@Param("makerUserName")String makerUserName);
	
	@Modifying
	@Query("UPDATE InspectionReport i SET i.userAction = :userAction, i.checkerUserName = :checkerUserName WHERE i.inspectionReportId = :inspectionReportId")
	int updateReportTemp(@Param("userAction") String userAction,
	                                    @Param("checkerUserName") String checkerUserName,
	                                    @Param("inspectionReportId") Long inspectionReportId);
	@Modifying
	@Query("UPDATE InspectionReport i SET i.userAction = :userAction, i.checkerUserName = :checkerUserName WHERE i.inspectionReportId = :inspectionReportId")
	int updateEditReportTemp(@Param("userAction")String userAction, @Param("checkerUserName")
			String checkerUserName, @Param("inspectionReportId")Long inspectionReportId);

	@Query("SELECT i FROM InspectionReport i WHERE i.userAction = '4'")
	List<InspectionReport> getAllEditReportList();

	/*
	 * @Query(value = "SELECT * FROM inspection_report_history", nativeQuery = true)
	 * List<InspectionReportDto> getAllViewReportList();
	 */
	
    @Query(value = "SELECT inspection_report_id, part_number, part_desc, purchase_order_no, supplier_name, report_no, date, qty, qty_receive, invoice_observation, manufacturer_cert_observation, "+
    " supplier_cert_observation, full_traceability_observation, batch_number_observation, date_of_manufacturing_observation, self_life_observation, tds_observation, material_condition_observation, "+
    " specification_observation, document_observation, lot_accepted, remark, maker_user_name, maker_date, checker_user_name, checker_date, date_of_expiry_observation "+
    " FROM inspection_report_history", nativeQuery = true)
	List<Object[]> getRawReportList();

	
	/*
	 * @Query(value =
	 * "SELECT COUNT(*) > 0 FROM inspection_report_history WHERE inspection_report_id = :reportId"
	 * , nativeQuery = true) boolean existsByReportIdHistory(@Param("reportId") Long
	 * reportId);
	 */

}
