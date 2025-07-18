package com.aeromaintenance.customerOrder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aeromaintenance.PurchaseRequisition.PurchaseRequisition;
import com.aeromaintenance.PurchaseRequisition.PurchaseRequisitionDTO;
import com.aeromaintenance.inspectionReport.InspectionReport;
import com.aeromaintenance.inspectionReport.InspectionReportDto;
import com.common.ResponseBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping(value="/api/customerOrder")
public class CustomerOrderController {
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private CustomerOrderService orderService;
	
	@PostMapping("/addCustomerOrder")
	public ResponseEntity<ResponseBean<Void>> saveBatch(@RequestBody List<CustomerOrderDto> orderDto) {
        List<CustomerOrder> entities = orderDto.stream()
                .map(CustomerOrderDto::toEntity)
                .collect(Collectors.toList());

        if(entities.size()<=10 && !entities.isEmpty()) {
            orderService.saveInBatches(entities, entities.size());
            ResponseBean<Void> response = new ResponseBean<>("200","Saved in batche " + entities.size() + " successfully.",null);
            return ResponseEntity.ok(response);
        }else {
            ResponseBean<Void> response = new ResponseBean<>("401","At a time 10 records can be added only Your Records count is :-  " + entities.size() ,null);
            return ResponseEntity.badRequest().body(response);
        }
    }

	@GetMapping("/getpendingCustomerOrderList")
	public ResponseEntity<List<CustomerOrder>>getPendingOrderList(){
		List<CustomerOrder> orders = customerOrderRepository.getAllPendingList();
		return ResponseEntity.ok(orders);
		}
	
	@PostMapping("/approveCustomerOrder")
	 public ResponseEntity<ResponseBean<Void>> approveReport(@RequestBody CustomerOrderDto order) {
	     
		 ResponseBean<Void> response=null;
	     if (order == null) {
	         response = new ResponseBean<>("404", "report not found", null);
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	     }

	     int rowsInserted = orderService.approveReport(order);

	     if (rowsInserted >= 1) {
	    	 if(order.getUserAction().equals("2")){
	    		 response = new ResponseBean<>("200", "customer order approved and moved to history successfully", null);
	    	 }if(order.getUserAction().equals("3")){
	    		response = new ResponseBean<>("200", "Customer Order Edited  successfully", null);
	    	 }	 	        
	         return ResponseEntity.ok(response);
	     }else if(rowsInserted == -1) {
	    	response = new ResponseBean<>("409", "Customer Order already exists in the database", null);
	         return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	     } else {
	    	 response= new ResponseBean<>("500", "Failed ", null);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }
	
	@GetMapping("/getCustomerOrderById/{reportId}")
	public ResponseEntity<CustomerOrder> getReportDetailById(@PathVariable  Long reportId){
		Optional<CustomerOrder> optionalReport = customerOrderRepository.findById(reportId);
		CustomerOrder report = optionalReport.orElseThrow(()  -> new RuntimeException("Details not found"));
		return ResponseEntity.ok(report);
		
	} 
	
	@DeleteMapping("/deleteCustomerOrder/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable Long id) {
    	CustomerOrder report = customerOrderRepository.findById(id).orElse(null);
        if (report != null) {
        	customerOrderRepository.deleteById(id);
            return ResponseEntity.ok("Customer Order deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	
	
	
	@PostMapping("/uploadWithOrders")
	public ResponseEntity<?> uploadOrdersWithDocument(
	    @RequestParam("document") MultipartFile file,
	    @RequestParam("orders") String orderList
	) throws IOException {

	    // Save the file
		SecureRandom secureRandom = new SecureRandom();
	    long orderNumber = Math.abs(secureRandom.nextLong());
        String uploadDir = "D:/CustomerOrders/uploads/" + orderNumber + "/";
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        //Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new JavaTimeModule());
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    List<CustomerOrderDto> orders = objectMapper.readValue(orderList,
	        new TypeReference<List<CustomerOrderDto>>() {});

	    // Save each with file reference
	    for (CustomerOrderDto dto : orders) {
	        CustomerOrder entity = new CustomerOrder();
	        entity.setOrderNo(orderNumber);
	        entity.setRoNo(dto.getRoNo());
	        entity.setRoReceiveDate(dto.getRoReceiveDate());
	        entity.setPartNo(dto.getPartNo());
	        entity.setPartDescription(dto.getPartDescription());
	        entity.setQuantity(dto.getQuantity());
	        entity.setSrNo(dto.getSrNo());
	        entity.setBatchNo(dto.getBatchNo());
	        entity.setStatus(dto.getStatus());
	        entity.setCustomerName(dto.getCustomerName());
	        entity.setDocumentPath("uploads/" + filePath); // or absolute path
	        entity.setMakerUserName(dto.getMakerUserName());
	        entity.setMakerDate(dto.getMakerDate());
	        entity.setUserRole(dto.getUserRole());
	        entity.setUserAction(dto.getUserAction());
	        customerOrderRepository.save(entity);
	    }

	    return ResponseEntity.ok("All data saved successfully.");
	}
	
	@GetMapping("/viewCustomerOrder")
    public ResponseEntity<List<CustomerOrderDto>> getViewOrderList() {
        List<CustomerOrderDto> reports = orderService.getAllViewOrderList();
        return ResponseEntity.ok(reports);
    }
	
	@GetMapping("/getEditOrderList")
    public ResponseEntity<List<CustomerOrder>> getEditReportList() {
        //System.out.println("userAction = " + userAction + ", userRole = " + userRole);

        List<CustomerOrder> orders = customerOrderRepository.getAllEditReportList();
        return ResponseEntity.ok(orders);
    }
	
	 @PutMapping("/updateOrder/{id}")
	    public ResponseEntity<CustomerOrder> updateReportrById(@PathVariable Long id, @RequestBody  CustomerOrder updateOrder) {
	    	CustomerOrder order = orderService.updateOrder(id, updateOrder);
	        if (order != null) {
	            return ResponseEntity.ok(order);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
}
