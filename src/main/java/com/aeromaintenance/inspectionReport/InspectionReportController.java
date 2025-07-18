package com.aeromaintenance.inspectionReport;

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

import com.aeromaintenance.customerOrder.CustomerOrderDto;
import com.aeromaintenance.login.MenuService;
import com.aeromaintenance.login.Role;
import com.aeromaintenance.login.RoleDTO;
import com.aeromaintenance.supplier.SupplierDto;
import com.aeromaintenance.supplier.SupplierModel;
import com.aeromaintenance.supplier.SupplierService;
import com.common.ResponseBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RestController
@RequestMapping("/api/inspectionReport")
public class InspectionReportController {
	@Autowired
    private InspectionReportService reportService;
	
	@Autowired
    private InspectionReportRepository inspectionReportRepository;
	
	@GetMapping("/partNo")
    public ResponseEntity<List<PartDetailsDTO>> getAllPartNo() {
        List<String> partNumbers = inspectionReportRepository.findAllPartNumber();

        if (partNumbers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartDetailsDTO> partDetailsDTO = partNumbers.stream()
                .map(partNumber -> new PartDetailsDTO(partNumber))
                .collect(Collectors.toList());

        return ResponseEntity.ok(partDetailsDTO);
    }
	
	@GetMapping("/getDetilsByPartNo/{partNo}")
	public ResponseEntity<PartDetailsDTO> getDetailsByPart(@PathVariable String partNo){
		Optional<PartDetailsDTO> partDetails = inspectionReportRepository.findDetailsByPartNumber(partNo);
		PartDetailsDTO partDetailsDTO = partDetails.orElseThrow(() -> new RuntimeException("Details not found"));
		return ResponseEntity.ok(partDetailsDTO);		
	}
	
	@PostMapping("/saveInspectionReport")
    public ResponseEntity<?> submitReport(@RequestParam("document") MultipartFile file,
    	    @RequestParam("report") String report) throws IOException {
		// Save the file
				SecureRandom secureRandom = new SecureRandom();
			    long orderNumber = Math.abs(secureRandom.nextLong());
		        String uploadDir = "D:/InspectionReports/uploads/" + orderNumber + "/";
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
			    InspectionReport reports = objectMapper.readValue(report,
			        new TypeReference<InspectionReport>() {});
		        reports.setDocumentPath("uploads/" + filePath);
		inspectionReportRepository.save(reports);
        return ResponseEntity.ok("Submitted");
    }
	
	@GetMapping("/getpendingInpectionReportList")
	public ResponseEntity<List<InspectionReport>>getPendingReportList(){
		List<InspectionReport> reports = inspectionReportRepository.getAllPendingList();
		return ResponseEntity.ok(reports);
		}
	
	@GetMapping("/getReportDetailById/{reportId}")
	public ResponseEntity<InspectionReport> getReportDetailById(@PathVariable  Long reportId){
		Optional<InspectionReport> optionalReport = inspectionReportRepository.findById(reportId);
		InspectionReport report = optionalReport.orElseThrow(()  -> new RuntimeException("Details not found"));
		return ResponseEntity.ok(report);
		
	}
	
	@PostMapping("/approveReport")
	 public ResponseEntity<ResponseBean<Void>> approveSupplier(@RequestBody InspectionReportDto report) {
	     
	     //SupplierModel supplierModel = supplierService.getSupplierById(supplierDto.getSupplierId());
		 ResponseBean<Void> response=null;
	     if (report == null) {
	         response = new ResponseBean<>("404", "Supplier not found", null);
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	     }

	     int rowsInserted = reportService.approveReport(report);

	     if (rowsInserted >= 1) {
	    	 if(report.getUserAction().equals("2")){
	    		 response = new ResponseBean<>("200", "Supplier approved and moved to history successfully", null);
	    	 }if(report.getUserAction().equals("3")){
	    		response = new ResponseBean<>("200", "Supplier Edited  successfully", null);
	    	 }	 	        
	         return ResponseEntity.ok(response);
	     }else if(rowsInserted == -1) {
	    	response = new ResponseBean<>("409", "Supplier already exists in the database", null);
	         return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	     } else {
	    	 response= new ResponseBean<>("500", "Failed ", null);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }
	@DeleteMapping("/deleteReport/{id}")
    public ResponseEntity<String> deleteReportById(@PathVariable Long id) {
    	InspectionReport report = inspectionReportRepository.findById(id).orElse(null);
        if (report != null) {
        	inspectionReportRepository.deleteById(id);
            return ResponseEntity.ok("Inspection Report deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	 @GetMapping("/getEditReportList")
	    public ResponseEntity<List<InspectionReport>> getEditReportList() {
	        //System.out.println("userAction = " + userAction + ", userRole = " + userRole);

	        List<InspectionReport> reports = inspectionReportRepository.getAllEditReportList();
	        return ResponseEntity.ok(reports);
	    }
	 
	 @PutMapping("/updateReport/{id}")
	    public ResponseEntity<InspectionReport> updateReportrById(@PathVariable Long id, @RequestBody InspectionReport updateReport) {
	    	InspectionReport report = reportService.updateReport(id, updateReport);
	        if (report != null) {
	            return ResponseEntity.ok(report);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	 @GetMapping("/viewReport")
	    public ResponseEntity<List<InspectionReportDto>> getViewReportList() {
	        //System.out.println("userAction = " + userAction + ", userRole = " + userRole);

	        List<InspectionReportDto> reports = reportService.getAllViewReportList();
	        return ResponseEntity.ok(reports);
	    }
	}
