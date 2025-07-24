package com.aeromaintenance.customerOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class CustomerOrderRepositoryCustomImpl implements CustomerOrderRepositoryCustom {

	@PersistenceContext
	 private EntityManager entityManager;
	
	@Override
	public int insertCustomerOrderHistory(CustomerOrderDto order) {
		Query query = entityManager.createNativeQuery(" INSERT INTO customer_order_history (sr_no, order_no, "+
			    " ro_no, ro_receive_date, customer_name, part_desc, part_no, "+
			    " batch_no, qty, status, workOrder, "+
			    " document_path, maker_user_name, maker_date, checker_user_name, checker_date, user_role, "+
			    " user_action, remark "+
			    " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" 
			    );
		    
		    query.setParameter(1, order.getSrNo());
		    query.setParameter(2, order.getOrderNo());
		    query.setParameter(3, order.getRoNo());
		    query.setParameter(4, order.getRoReceiveDate());
		    query.setParameter(5, order.getCustomerName());
		    query.setParameter(6, order.getPartDescription());
		    query.setParameter(7, order.getPartNo());

		    query.setParameter(8, order.getBatchNo());
		    query.setParameter(9, order.getQuantity());
		    query.setParameter(10, order.getStatus());

		    query.setParameter(11, 0);
		    query.setParameter(12, order.getDocumentPath());
		    query.setParameter(13, order.getMakerUserName());

		    query.setParameter(14, order.getMakerDate());
		    query.setParameter(15, order.getCheckerUserName());
		    query.setParameter(16, order.getCheckerDate());
		    query.setParameter(17, order.getUserRole());
		    query.setParameter(18, order.getUserAction());

		    query.setParameter(19, order.getRemark());
		    
		    int result =  query.executeUpdate();
		     return result;
	}

	@Override
	public boolean existsBySrNo(Long srNo) {
		Number count = (Number) entityManager.createNativeQuery(
	            "SELECT COUNT(*) FROM customer_order_history WHERE sr_no = ?")
	            .setParameter(1, srNo)
	            .getSingleResult();
	    System.out.println("count is :-" + count);	
	    return count.intValue() > 0;
	}

	@Override
	public int updateCustomerOrderTemp(String userAction, Long srNo) {
		int result=0;
		 try {
			  Query query = entityManager.createNativeQuery("UPDATE customer_order SET user_action = ? WHERE sr_no =?");
			  query.setParameter(1, userAction);
			 query.setParameter(2, srNo);
			 result=query.executeUpdate();
		}catch(Exception e) {
			 e.printStackTrace();
		 }
		 return result;
	}

	@Override
	public int updateEditCustomerOrderTemp(String actionCode, Long srNo) {
		
			 int result =0;
			 try {
				 Query query = entityManager.createNativeQuery("UPDATE customer_order SET user_action = ? WHERE sr_no =?");
			     query.setParameter(1,actionCode);
			     query.setParameter(2,srNo);
			     result = query.executeUpdate();
			     System.out.println("UserAction:-" + actionCode);
			     }catch(Exception e) {
			    	 e.printStackTrace();
			     }
			 System.out.println(result);
			 return result;
	}

//Query for the work ordr data calling in the work order
	@Override
public List<CustomerOrderHistoryDTO> findAllHistoryWithWorkOrderZero() {
    Query query = entityManager.createNativeQuery(
        "SELECT order_no, sr_no, customer_name, part_desc, part_no, qty, status, workOrder, document_path, maker_user_name, maker_date, checker_user_name, checker_date, user_role, user_action, remark FROM customer_order_history WHERE workOrder = 0");

    List<Object[]> rows = query.getResultList();
    List<CustomerOrderHistoryDTO> list = new ArrayList<>();

    for (Object[] row : rows) {
        CustomerOrderHistoryDTO dto = new CustomerOrderHistoryDTO(
            (String) row[0],   // order_no
            (String) row[1],   // sr_no
            (String) row[2],   // customer_name
            (String) row[3],   // part_desc
            (String) row[4],   // part_no
            row[5] != null ? ((Number) row[5]).longValue() : null, // ✅ FIX: qty (cast safely)
            (String) row[6],   // status
            row[7] != null ? (Boolean) row[7] : false,             // ✅ Handle possible null
            (String) row[8],   // document_path
            (String) row[9],   // maker_user_name
            (Timestamp) row[10], // maker_date
            (String) row[11],  // checker_user_name
            (Timestamp) row[12], // checker_date
            (String) row[13],  // user_role
            (String) row[14],  // user_action
            (String) row[15]   // remark
        );
        list.add(dto);
    }

    return list;
}

}