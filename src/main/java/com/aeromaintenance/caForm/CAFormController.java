package com.aeromaintenance.caForm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aeromaintenance.inspectionReport.InspectionReport;
import com.aeromaintenance.inspectionReport.PartDetailsDTO;

@RestController
@RequestMapping(value="/api/caForm")
public class CAFormController {
	
	@Autowired
	private CAFormRepository repository;
	
	@Autowired
	private CAFormService service;

	@GetMapping("/workOrderList")
    public ResponseEntity<List<workOrderDetailDto>> getAllWorkOrder() {
        List<String> workOrders = repository.findAllWorkOrder();

        if (workOrders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<workOrderDetailDto> workOrderList = workOrders.stream()
                .map(workOrder -> new workOrderDetailDto(workOrder))
                .collect(Collectors.toList());

        return ResponseEntity.ok(workOrderList);
    }
	
	@GetMapping("/getDetilsByWorkOrderNo/{workOrder}")
	public ResponseEntity<workOrderDetailDto> getDetailsByWorkOrder(@PathVariable String workOrder){
		Optional<workOrderDetailDto> workOrderDetails = repository.findDetailsByWorkOrder(workOrder);
		workOrderDetailDto workOrderDetailsDTO = workOrderDetails.orElseThrow(() -> new RuntimeException("Details not found"));
		return ResponseEntity.ok(workOrderDetailsDTO);		
	}
	
	@PostMapping("/saveCAForm")
	public ResponseEntity<?> submitCAForm(@RequestBody CAForm caForm){
		
		repository.save(caForm);
		
		return ResponseEntity.ok("Submitted");
	}
	
	@GetMapping("/viewCAForm")
	public ResponseEntity<List<CAForm>> viewCAFormList(){
		List<CAForm> list = repository.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getCAFormByID/{id}")
	public ResponseEntity<CAForm> getCAFormByID(@PathVariable Long id ){
		Optional<CAForm> data= repository.findById(id);
		CAForm caForm = data.orElseThrow(() -> new RuntimeException("Data not Found"));
		return ResponseEntity.ok(caForm);
	}
	
	@DeleteMapping("/deleteCAFormByID/{id}")
	public ResponseEntity<?> deleteCAForm(@PathVariable Long id){
		CAForm caForm = repository.findById(id).orElse(null);
		if(caForm != null) {
			repository.deleteById(id);
			return ResponseEntity.ok("CA Form deleted successfully");
		}else {
            return ResponseEntity.notFound().build();
        }
	}
	
	 @PutMapping("/updateCAForm/{id}")
	 public ResponseEntity<CAForm> updateCAFormById(@PathVariable Long id, @RequestBody CAForm updateReport) {
	    	CAForm report = service.updateReport(id, updateReport);
	        if (report != null) {
	            return ResponseEntity.ok(report);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } 
}
