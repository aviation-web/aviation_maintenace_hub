package com.aeromaintenance.DispatchReport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DispatchReportService {

    @Autowired
    private DispatchReportRepository repository;

    // Convert Entity to DTO
    private DispatchReportDTO convertToDTO(DispatchReport report) {
        return new DispatchReportDTO(
            report.getId(),
            report.getReportNo(),
            report.getDate(),
            report.getPartNo(),
            report.getPartDescription(),
            report.getOrderNo(),
            report.getCustomerName(),
            report.getQuantity(),
            report.getBatchNo(),
            report.getChallanNo(),
            report.getChallanDate(),
            report.getChallanRemark(),
            report.getInvoiceNo(),
            report.getInvoiceDate(),
            report.getInvoiceRemark(),
            report.getCaForm(),
            report.getCaFormDate(),
            report.getCaFormRemark(),
            report.getEwayBill(),
            report.getEwayBillDate(),
            report.getEwayBillRemark(),
            report.getStoresInChargeName(),
            report.getStoresInChargeSign()
        );
    }

    // Convert DTO to Entity
    private DispatchReport convertToEntity(DispatchReportDTO dto) {
        return new DispatchReport(
            dto.getId(),
            dto.getReportNo(),
            dto.getReportDate(),
            dto.getPartNo(),
            dto.getPartDescription(),
            dto.getOrderNo(),
            dto.getCustomerName(),
            dto.getQuantity(),
            dto.getBatchNo(),
            dto.getChallanNo(),
            dto.getChallanDate(),
                dto.getChallanRemark(),
            dto.getInvoiceNo(),
                dto.getInvoiceDate(),
                dto.getInvoiceRemark(),
                dto.getCaFormDate(),
            dto.getCaFormNo(),
                dto.getCaFormRemark(),
            dto.getEwayBill(),
                dto.getEwayBillDate(),
                dto.getEwayBillRemark(),
            dto.getStoresInChargeName(),
            dto.getStoresInChargeSign()
        );
    }

    // Create a new Dispatch Report
    public DispatchReportDTO saveReport(DispatchReportDTO dto) {
        DispatchReport savedReport = repository.save(convertToEntity(dto));
        return convertToDTO(savedReport);
    }

    // Get all Dispatch Reports
    public List<DispatchReportDTO> getAllReports() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get a Dispatch Report by ID
    public DispatchReportDTO getReportById(Long id) {
        Optional<DispatchReport> report = repository.findById(id);
        return report.map(this::convertToDTO).orElse(null);
    }

    // Update a Dispatch Report
    public DispatchReportDTO updateReport(Long id, DispatchReportDTO dto) {
        if (repository.existsById(id)) {
            dto.setId(id);
            DispatchReport updatedReport = repository.save(convertToEntity(dto));
            return convertToDTO(updatedReport);
        }
        return null;
    }

    // Delete a Dispatch Report
    public boolean deleteReport(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
