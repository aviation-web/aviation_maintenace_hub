package com.aeromaintenance.PurchaseRequisition;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PurchaseRequisitionService {
    @Autowired
    private PurchaseRequisitionRepository repository;

    // Method to get Purchase Requisition by ID
    public PurchaseRequisitionDTO getPurchaseRequisitionById(Long id) {
        PurchaseRequisition entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    // Create new Purchase Requisition
    @Transactional
    public PurchaseRequisitionDTO createPurchaseRequisition(PurchaseRequisitionDTO dto) {
        PurchaseRequisition entity = new PurchaseRequisition();
        BeanUtils.copyProperties(dto, entity);

        PurchaseRequisition savedEntity = repository.save(entity);

        PurchaseRequisitionDTO savedDto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(savedEntity, savedDto);

        return savedDto;
    }
    
 // View Purchase Requisition by ID
    public PurchaseRequisitionDTO viewPurchaseRequisitionById(Long id) {
        // Find the entity
        PurchaseRequisition entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        // Convert to DTO
        PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


    // Get All Purchase Requisitions
    public List<PurchaseRequisitionDTO> getAllPurchaseRequisitions() {
        return repository.findAll().stream()
            .map(entity -> {
                PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
                BeanUtils.copyProperties(entity, dto);
                return dto;
            })
            .collect(Collectors.toList());
    }

    // Update Purchase Requisition
    @Transactional
    public PurchaseRequisitionDTO updatePurchaseRequisition(Long id, PurchaseRequisitionDTO dto) {
        // Find the existing entity
        PurchaseRequisition existingEntity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Purchase Requisition not found with id: " + id));

        // Explicitly set the ID to prevent null ID issue
        dto.setId(id);

        // Copy properties, ignoring null values
        BeanUtils.copyProperties(dto, existingEntity, getNullPropertyNames(dto));

        // Save the updated entity
        PurchaseRequisition updatedEntity = repository.save(existingEntity);

        // Convert back to DTO
        PurchaseRequisitionDTO updatedDto = new PurchaseRequisitionDTO();
        BeanUtils.copyProperties(updatedEntity, updatedDto);

        return updatedDto;
    }

    // Delete Purchase Requisition
    @Transactional
    public void deletePurchaseRequisition(Long id) {
        // Check if entity exists before deleting
        if (!repository.existsById(id)) {
            throw new RuntimeException("Purchase Requisition not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // Utility method to get null property names
    private String[] getNullPropertyNames(Object source) {
        final org.springframework.beans.BeanWrapper src = new org.springframework.beans.BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // Batch wise save in db
    public List<String> saveInBatches(List<PurchaseRequisition> requisitions, int batchSize) {
        List<String> batchInfoList = new ArrayList<>();

        for (int i = 0; i < requisitions.size(); i += batchSize) {
            int end = Math.min(i + batchSize, requisitions.size());
            List<PurchaseRequisition> batch = requisitions.subList(i, end);

            String batchNumber = "BATCH_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            int batchSizeActual = batch.size(); // Store how many records are in this batch

            for (PurchaseRequisition req : batch) {
                req.setBatchNumber(batchNumber);
            }

            repository.saveAll(batch);

            // Store info: batch number + size
            batchInfoList.add(batchNumber + " : " + batchSizeActual);
        }

        return batchInfoList;
    }
    
    public List<PurchaseRequisition> getRequisitionsByBatchNo(String batchNumber) {
        return repository.findByBatchNumber(batchNumber);
    }
    


    // Creating downloadable file
    public String exportRequisitionsToPDFFile() {
        try {
            // Create directory if not exists
            File folder = new File("D:/download");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Generate unique filename using timestamp
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(fileFormatter);
            String filePath = "D:/download/purchase_requisitions_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Purchase Requisitions Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            Stream.of("ID", "SR No", "Part Number", "Description", "Current Stock", "Required Qty", "Required Date", "Remark", "Created Date")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header));
                        cell.setBackgroundColor(Color.LIGHT_GRAY);
                        table.addCell(cell);
                    });

            List<PurchaseRequisition> requisitions = repository.findAll();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (PurchaseRequisition pr : requisitions) {
                table.addCell(pr.getId().toString());
                table.addCell(pr.getSrNo().toString());
                table.addCell(pr.getPartNumber());
                table.addCell(pr.getDescription());
                table.addCell(pr.getCurrentStock().toString());
                table.addCell(pr.getRequiredQty().toString());
                table.addCell(pr.getRequiredDate().format(dateFormat));
                table.addCell(pr.getRemark() != null ? pr.getRemark() : "-");
                table.addCell(pr.getCreatedDate().format(dtFormat));
            }

            document.add(table);
            document.close();

            return "PDF saved at: " + filePath;

        } catch (Exception e) {
            e.printStackTrace();
            return "PDF generation failed: " + e.getMessage();
        }
    }

    private List<PurchaseRequisition> getAllRequisitions() {
        // ACTUAL IMPLEMENTATION TO GET DATA FROM DATABASE
        return repository.findAll(); // This should return your actual data
    }

    public List<PurchaseRequisition> getByBatchNumberForPurchaseOrder(String batchNumber) {
        return repository.findByBatchNumber(batchNumber);
    }

    public String exportToCSV() {
        try {
            // Ensure the folder exists
            File folder = new File("D:/download");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create unique filename
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(formatter);
            String filePath = "D:/download/purchase_requisitions_" + timestamp + ".csv";

            FileWriter writer = new FileWriter(filePath);

            // CSV header
            writer.write("ID,SR No,Part Number,Description,Current Stock,Required Qty,Required Date,Remark,Created Date\n");

            List<PurchaseRequisition> requisitions = repository.findAll();

            for (PurchaseRequisition pr : requisitions) {
                writer.write(pr.getId() + "," +
                        pr.getSrNo() + "," +
                        escapeCSV(pr.getPartNumber()) + "," +
                        escapeCSV(pr.getDescription()) + "," +
                        pr.getCurrentStock() + "," +
                        pr.getRequiredQty() + "," +
                        pr.getRequiredDate() + "," +
                        escapeCSV(pr.getRemark()) + "," +
                        pr.getCreatedDate() + "\n");
            }

            writer.close();
            return "CSV saved at: " + filePath;

        } catch (Exception e) {
            e.printStackTrace();
            return "CSV generation failed: " + e.getMessage();
        }
    }

    private String escapeCSV(String input) {
        if (input == null) return "";
        return "\"" + input.replace("\"", "\"\"") + "\"";
    }

}