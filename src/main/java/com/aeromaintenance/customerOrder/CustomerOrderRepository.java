package com.aeromaintenance.customerOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aeromaintenance.inspectionReport.InspectionReport;

import javax.transaction.Transactional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String> {

	@Query("SELECT i FROM CustomerOrder i WHERE i.userAction = '1' AND makerUserName <> :makerUserName")
	List<CustomerOrder> getAllPendingList(@Param("makerUserName")String makerUserName);

	@Query(value = "SELECT sr_no, order_no, ro_no, ro_receive_date, customer_name, part_desc, part_no,"
			+ " batch_no, qty, status, document_path, maker_user_name, maker_date, checker_user_name,"
			+ " checker_date, user_role, user_action, remark "
		    + " FROM customer_order_history", nativeQuery = true)
			List<Object[]> getRawReportList();

	@Query("SELECT i FROM CustomerOrder i WHERE i.userAction = '4'")
	List<CustomerOrder> getAllEditReportList();


	@Transactional
	@Modifying
	@Query("UPDATE CustomerOrder c SET c.status = :status WHERE c.srNo = :srNo")
	void updateStatusBySrNo(String srNo, String status);
}
