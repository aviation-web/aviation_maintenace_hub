package com.aeromaintenance.inspectionReport;

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
import org.springframework.web.bind.annotation.RestController;

import com.common.ResponseBean;

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
    public ResponseEntity<?> submitReport(@RequestBody InspectionReport report) {
		inspectionReportRepository.save(report);
		reportService.saveInspectionDataInStore(report);
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
