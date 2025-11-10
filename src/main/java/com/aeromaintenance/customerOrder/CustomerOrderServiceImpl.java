package com.aeromaintenance.customerOrder;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.aeromaintenance.exception.DuplicateRoNoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aeromaintenance.PurchaseRequisition.PurchaseRequisition;
import com.aeromaintenance.inspectionReport.InspectionReportDto;

import javax.transaction.Transactional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService{

	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private CustomerOrderRepositoryCustom customerOrderRepositoryCustom;
	
//	@Override
//	public List<String> saveInBatches(List<CustomerOrder> orders, int batchSize) {
//		List<String> batchInfoList = new ArrayList<>();
//
//        for (int i = 0; i < orders.size(); i += batchSize) {
//            int end = Math.min(i + batchSize, orders.size());
//            List<CustomerOrder> batch = orders.subList(i, end);
//
//            SecureRandom secureRandom = new SecureRandom();
//            long batchNumber = secureRandom.nextLong();
//
//            int batchSizeActual = batch.size(); // Store how many records are in this batch
//
//            for (CustomerOrder req : batch) {
//                req.setOrderNo(batchNumber);
//            }
//
//            customerOrderRepository.saveAll(batch);
//
//            // Store info: batch number + size
//            batchInfoList.add(batchNumber + " : " + batchSizeActual);
//        }
//
//        return batchInfoList;
//
//	}

//	@Override
//	public List<String> saveInBatches(List<CustomerOrder> orders, int batchSize) {
//		List<String> batchInfoList = new ArrayList<>();
//
//		for (int i = 0; i < orders.size(); i += batchSize) {
//			int end = Math.min(i + batchSize, orders.size());
//			List<CustomerOrder> batch = orders.subList(i, end);
//
//			// Generate unique batch number
//			SecureRandom secureRandom = new SecureRandom();
//			long batchNumber = Math.abs(secureRandom.nextLong());
//
//			// Collect all roNos from this batch
//			List<String> roNos = batch.stream()
//					.map(CustomerOrder::getRoNo)
//					.filter(Objects::nonNull)
//					.collect(Collectors.toList());
//
//			// Fetch existing roNos from DB
//			List<String> existingRoNos = customerOrderRepository.findExistingRoNos(roNos);
//
//			// Filter unique records only (exclude duplicates)
//			List<CustomerOrder> uniqueOrders = batch.stream()
//					.filter(order -> !existingRoNos.contains(order.getRoNo()))
//					.collect(Collectors.toList());
//
//			// Assign batch number to each valid record
//			for (CustomerOrder order : uniqueOrders) {
//				order.setOrderNo(batchNumber);
//				if (order.getStatus() == null) {
//					order.setStatus("OPEN");
//				}
//			}
//
//			// Save only non-duplicate orders
//			if (!uniqueOrders.isEmpty()) {
//				customerOrderRepository.saveAll(uniqueOrders);
//			}
//
//			// Prepare batch info summary
//			String info = "Batch " + batchNumber + " : Saved " + uniqueOrders.size() +
//					" of " + batch.size() + " (Skipped " + (batch.size() - uniqueOrders.size()) + " duplicates)";
//			batchInfoList.add(info);
//
//			// Log duplicate roNos
//			if (!existingRoNos.isEmpty()) {
//				System.out.println("⚠️ Duplicate roNos skipped in batch " + batchNumber + ": " + existingRoNos);
//			}
//		}
//
//		return batchInfoList;
//	}

	@Override
	@Transactional
	public List<String> saveInBatches(List<CustomerOrder> orders, int batchSize) {
		List<String> batchInfoList = new ArrayList<>();

		for (int i = 0; i < orders.size(); i += batchSize) {
			int end = Math.min(i + batchSize, orders.size());
			List<CustomerOrder> batch = orders.subList(i, end);

			SecureRandom secureRandom = new SecureRandom();
			long batchNumber = Math.abs(secureRandom.nextLong());

			// Get all roNos in this batch
			List<String> roNos = batch.stream()
					.map(CustomerOrder::getRoNo)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());

			// Check if any already exist in DB
			List<String> existingRoNos = customerOrderRepository.findExistingRoNos(roNos);

			// 🚫 If duplicates found — throw error
			if (!existingRoNos.isEmpty()) {
				throw new DuplicateRoNoException(
						"Duplicate RO Numbers found: " + String.join(", ", existingRoNos)
				);
			}

			// Assign batch number and default status
			for (CustomerOrder order : batch) {
				order.setOrderNo(batchNumber);
				if (order.getStatus() == null) {
					order.setStatus("OPEN");
				}
			}

			// Save all in this batch
			customerOrderRepository.saveAll(batch);

			// Add summary info
			batchInfoList.add("Batch " + batchNumber + " : Saved " + batch.size());
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

				String sr_no = (String) row[0];
				Long order_no = row[1] != null ? Long.parseLong(row[1].toString()) : null;
				String ro_no = (String) row[2];
				String ro_receive_date = (String) row[3];
				String ro_date = (String) row[4];
				String customer_name = (String) row[5];
				String part_desc = (String) row[6];
				String part_no = (String) row[7];
				String batch_no = (String) row[8];
				Integer qty = row[9] != null ? ((Number) row[9]).intValue() : null;
				String status = (String) row[10];
				String document_path = (String) row[11];

				LocalDate maker_date = row[12] != null
						? ((Timestamp) row[12]).toLocalDateTime().toLocalDate()
						: null;

				String maker_user_name = (String) row[13];

				LocalDate checker_date = row[14] != null
						? ((Timestamp) row[14]).toLocalDateTime().toLocalDate()
						: null;

				String checker_user_name = (String) row[15];
				String user_role = (String) row[16];
				String user_action = (String) row[17];
				String remark = (String) row[18];
				Integer backOrder = (Integer) row[19];
				String cmmRefNo = row.length > 20 ? (String) row[20] : "";

				CustomerOrderDto dto = CustomerOrderDto.builder()
						.srNo(sr_no)
						.orderNo(order_no)
						.batchNo(batch_no)
						.roNo(ro_no)
						.roReceiveDate(ro_receive_date)
						.roDate(ro_date)
						.customerName(customer_name)
						.partNo(part_no)
						.backOrder(backOrder)
						.partDescription(part_desc)
						.quantity(qty)
						.status(status)
						.documentPath(document_path)
						.makerUserName(maker_user_name)
						.makerDate(maker_date)
						.userRole(user_role)
						.userAction(user_action)
						.checkerUserName(checker_user_name)
						.checkerDate(checker_date)
						.remark(remark)
						.cmmRefNo(cmmRefNo)
						.build();

				result.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
			if (updateOrder.getBackOrder()!= null) {
				updateExisting.setBackOrder(updateOrder.getBackOrder());
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
