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
            Long sr_no = Long.valueOf((String) row[0]);
            Long order_no = Long.valueOf((String) row[1]);
            Long ro_no = ((Number) row[2]).longValue();
            String ro_receive_date = (String) row[3];
            String customer_name = (String) row[4];
        	String part_desc = (String) row[5];
        	String part_no = (String) row[6];
        	Long batch_no = ((Number) row[7]).longValue();
        	Integer qty = ((Number) row[8]).intValue();
            String status = (String) row[9];
            String document_path = (String) row[10];
            String maker_user_name = (String) row[11];
            LocalDate maker_date = row[12] != null ? ((Timestamp) row[12]).toLocalDateTime().toLocalDate() : null;
            String checker_user_name = (String) row[13];
            LocalDate checker_date = row[14] != null ? ((Timestamp) row[14]).toLocalDateTime().toLocalDate() : null;
            String user_role = (String) row[15];
        	String user_action = (String) row[16];
        	String remark = (String) row[17];
     
            result.add(new CustomerOrderDto(sr_no, order_no, batch_no, ro_no, ro_receive_date, customer_name,
            		part_no, part_desc, qty, status, document_path, maker_user_name,
            		maker_date, user_action, user_role, checker_user_name, checker_date, remark));
        }
		}catch(Exception e) {
			System.out.print(e);
		}

        return result;
	}

}
