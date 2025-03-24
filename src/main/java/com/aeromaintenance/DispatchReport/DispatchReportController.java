package com.aeromaintenance.DispatchReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispatch")
//@CrossOrigin(origins = "*") // Allow frontend requests
public class DispatchReportController {

	
	public DispatchReportController() {
		System.out.println("DispatchReportController Initilizing..");
		// TODO Auto-generated constructor stub
	}
	
	
    @Autowired
    private DispatchReportService service;

    @PostMapping("/save")
    public DispatchReportDTO saveReport(@RequestBody DispatchReportDTO dto) {
        return service.saveReport(dto);
    }

    @GetMapping("/all")
    public List<DispatchReportDTO> getAllReports() {
        return service.getAllReports();
    }

    @GetMapping("/{id}")
    public DispatchReportDTO getReportById(@PathVariable Long id) {
        return service.getReportById(id);
    }

    @PutMapping("/update/{id}")
    public DispatchReportDTO updateReport(@PathVariable Long id, @RequestBody DispatchReportDTO dto) {
        return service.updateReport(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        boolean isDeleted = service.deleteReport(id);
        return isDeleted ? "Report Deleted!" : "Report Not Found!";
    }

	
}