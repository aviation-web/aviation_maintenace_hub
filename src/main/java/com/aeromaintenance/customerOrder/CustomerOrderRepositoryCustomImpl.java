package com.aeromaintenance.customerOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

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
	public int updateEditCustomerOrderTemp(String userAction, Long srNo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
