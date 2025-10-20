package com.aeromaintenance.customerOrder;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aeromaintenance.PurchaseRequisition.PurchaseRequisition;
import com.aeromaintenance.inspectionReport.InspectionReportDto;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService{

	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private CustomerOrderRepositoryCustom customerOrderRepositoryCustom;
	
	@Override
	public List<String> saveInBatches(List<CustomerOrder> orders, int batchSize) {
		List<String> batchInfoList = new ArrayList<>();

        for (int i = 0; i < orders.size(); i += batchSize) {
            int end = Math.min(i + batchSize, orders.size());
            List<CustomerOrder> batch = orders.subList(i, end);
            
            SecureRandom secureRandom = new SecureRandom();
            long batchNumber = secureRandom.nextLong();
            
            int batchSizeActual = batch.size(); // Store how many records are in this batch

            for (CustomerOrder req : batch) {
                req.setOrderNo(batchNumber);
            }

            customerOrderRepository.saveAll(batch);

            // Store info: batch number + size
            batchInfoList.add(batchNumber + " : " + batchSizeActual);
        }

        return batchInfoList;
		
	}

	@Override
	public int approveReport(CustomerOrderDto order) {

		try {
			 int result=0;
			 int count=0;
			 if(order.getUserAction().equalsIgnoreCase("2")) {
				 System.out.println(order.getSrNo());
				 if(!customerOrderRepositoryCustom.existsBySrNo(order.getSrNo())) {
					 result = customerOrderRepositoryCustom.insertCustomerOrderHistory(order);
					  if(result>0) {
						 count=customerOrderRepositoryCustom.updateCustomerOrderTemp(order.getUserAction(), order.getSrNo());
						 }
				 }else {
					 //duplicate records
					 System.out.println("Allredy Sr. No. present in the database");	
				return -1;	 
				 }
			}else {  //if(report.getUserAction().equals("3")) {
				
				result =customerOrderRepositoryCustom.updateEditCustomerOrderTemp(order.getUserAction(), order.getSrNo());
				 }
			 return result;
			 
		 }catch(Exception e) {
			 e.printStackTrace();	 }
		 
		 return 0;

	
	}
	
	public List<CustomerOrderDto> getAllViewOrderList() {
		
		List<CustomerOrderDto> result = new ArrayList<>();
		try {
		List<Object[]> rawData = customerOrderRepository.getRawReportList();

        for (Object[] row : rawData) {
        	System.out.println("row[0] type: " + row[0].getClass().getName());
            String sr_no = ((String) row[0]);
            Long order_no = Long.valueOf((String) row[1]);
            String ro_no = (String) row[2];
            String ro_receive_date = (String) row[3];
			String ro_date = (String) row[4];
            String customer_name = (String) row[5];
        	String part_desc = (String) row[6];
        	String part_no = (String) row[7];
        	String batch_no = (String) row[8];
        	Integer qty = ((Number) row[9]).intValue();
            String status = (String) row[10];
            String document_path = (String) row[11];
            String maker_user_name = (String) row[12];
            LocalDate maker_date = row[13] != null ? ((Timestamp) row[12]).toLocalDateTime().toLocalDate() : null;
            String checker_user_name = (String) row[14];
            LocalDate checker_date = row[15] != null ? ((Timestamp) row[14]).toLocalDateTime().toLocalDate() : null;
            String user_role = (String) row[16];
        	String user_action = (String) row[17];
        	String remark = (String) row[18];
     
            result.add(new CustomerOrderDto(sr_no, order_no, batch_no, ro_no, ro_receive_date, ro_date, customer_name,
            		part_no, part_desc, qty, status, document_path, maker_user_name,
            		maker_date, user_action, user_role, checker_user_name, checker_date, remark));
        }
		}catch(Exception e) {
			System.out.print(e);
		}

        return result;
	}

	@Override
	public CustomerOrder updateOrder(String id, CustomerOrder updateOrder) {

		return customerOrderRepository.findById(id).map(updateExisting -> {
	    	  // Only update the fields that should be updated
	        if (updateOrder.getSrNo()!= null) {
	            updateExisting.setSrNo(updateOrder.getSrNo());
	        }
	        if (updateOrder.getPartDescription()!= null) {
	            updateExisting.setPartDescription(updateOrder.getPartDescription());
	        }
	        if (updateOrder.getOrderNo()!= null) {
	            updateExisting.setOrderNo(updateOrder.getOrderNo());
	        }
	        
	        if (updateOrder.getPartNo() != null) {
	            updateExisting.setPartNo(updateOrder.getPartNo());
	        }
	        
	        if (updateOrder.getRoNo()!= null) {
	            updateExisting.setRoNo(updateOrder.getRoNo());
	        }
	        
	        if (updateOrder.getRoReceiveDate()!= null) {
	            updateExisting.setRoReceiveDate(updateOrder.getRoReceiveDate());
	        }
			if (updateOrder.getRoDate()!= null) {
				updateExisting.setRoDate(updateOrder.getRoDate());
			}
	        if (updateOrder.getCustomerName()!= null) {
	            updateExisting.setCustomerName(updateOrder.getCustomerName());
	        }
	        
	        if (updateOrder.getQuantity() != null) {
	            updateExisting.setQuantity(updateOrder.getQuantity());
	        }
	        
	        if (updateOrder.getStatus()!= null) {
	            updateExisting.setStatus(updateOrder.getStatus());
	        }
	        
	        if (updateOrder.getBatchNo()!= null) {
	            updateExisting.setBatchNo(updateOrder.getBatchNo());
	        }
	        
	        if (updateOrder.getRemark() != null) {
	            updateExisting.setRemark(updateOrder.getRemark());
	        }
	        if (updateOrder.getMakerUserName() != null) {
	            updateExisting.setMakerUserName(updateOrder.getMakerUserName());
	        }
	        if (updateOrder.getMakerDate() != null) {
	            updateExisting.setMakerDate(updateOrder.getMakerDate());
	        }
	        if (updateOrder.getUserAction() != null) {
	        	 updateExisting.setUserAction(updateOrder.getUserAction());
	        }
	        if (updateOrder.getUserRole() != null) {
	            updateExisting.setUserRole(updateOrder.getUserRole());
	        }

	        return customerOrderRepository.save(updateExisting);
	    }).orElse(null); 
	
	}

}
