package com.aeromaintenance.PurchaseRequisition;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


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
//        return repository.findAll().stream()
//            .map(entity -> {
//                PurchaseRequisitionDTO dto = new PurchaseRequisitionDTO();
//                BeanUtils.copyProperties(entity, dto);
//                return dto;
//            })
//            .collect(Collectors.toList());
    	
    	
    	return repository.findByStatus("Open").stream()
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

         // Get the current year's last two digits
            String yearSuffix = String.valueOf(java.time.Year.now().getValue());

            // Fetch the latest MRN number from DB (descending order)
            List<String> prs = repository.findLatestBatchNo();
            String lastPr = prs.isEmpty() ? null : prs.get(0);


            int nextNumber = 1; // default starting number

            if (lastPr != null && lastPr.endsWith(yearSuffix)) {
                String numericPart = lastPr.substring(12, 16);
                try {
                    nextNumber = Integer.parseInt(numericPart) + 1;
                } catch (NumberFormatException e) {
                    nextNumber = 1;
                }
            }

            // Format the numeric portion with leading zeros
            String formattedNumber = String.format("%04d", nextNumber);

            // Create the new MRN number (e.g., MRN_00000225)
            String batchNumber = "AMC-PR-" + yearSuffix +"-"+ formattedNumber;

            // Double-check if MRN already exists (just to be safe)
            while (repository.existsByBatchNumber(batchNumber)) {
                nextNumber++;
                formattedNumber = String.format("%04d", nextNumber);
                batchNumber = "AMC-PR-" + yearSuffix +"-"+ formattedNumber;
            }
           // String batchNumber = "PR_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
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
    
    
    public void exportRequisitionsToPDFFile(HttpServletResponse response) {
    	 Document document = null;
    	    try {
    	        // 1. Response headers
    	        response.setContentType("application/pdf");
    	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    	        response.setHeader("Content-Disposition", "attachment; filename=purchase_requisitions_" + timestamp + ".pdf");

    	        // 2. Create PDF doc
    	        document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
    	        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
    	        writer.setCloseStream(false); // prevent auto-closing servlet stream
    	        document.open();

    	        // 3. Title
    	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    	        Paragraph title = new Paragraph("Purchase Requisitions Report", titleFont);
    	        title.setAlignment(Element.ALIGN_CENTER);
    	        title.setSpacingAfter(15f);
    	        document.add(title);

    	        // 4. Table setup
    	        PdfPTable table = new PdfPTable(9);
    	        table.setWidthPercentage(100);
    	        table.setWidths(new float[]{1.5f, 1.5f, 2f, 5f, 1.5f, 1.5f, 2f, 3f, 2.5f});
    	        table.setHeaderRows(1);
    	        table.setSplitRows(true);
    	        table.setSplitLate(false);

    	        // 5. Header row
    	        String[] headers = {
    	                "PR_Number", "P_REQ_NO", "Part Number", "Description",
    	                "Current Stock", "Required Qty", "Required Date", "Remark", "Created Date"
    	        };
    	        for (String h : headers) {
    	            PdfPCell cell = new PdfPCell(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9)));
    	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    	            cell.setPadding(4f);
    	            table.addCell(cell);
    	        }

    	        // 6. Data
    	        List<PurchaseRequisition> requisitions = repository.findByStatus("Open");
    	        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	        DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    	        if (requisitions.isEmpty()) {
    	            PdfPCell noData = new PdfPCell(new Phrase("No requisitions available", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)));
    	            noData.setColspan(9);
    	            noData.setHorizontalAlignment(Element.ALIGN_CENTER);
    	            noData.setPadding(8f);
    	            table.addCell(noData);
    	        } else {
    	            for (PurchaseRequisition pr : requisitions) {
    	                table.addCell(createCell(pr.getBatchNumber()));
    	                table.addCell(createCell(pr.getId() != null ? pr.getId().toString() : "-"));
    	                table.addCell(createCell(pr.getPartNumber()));
    	                table.addCell(createCell(pr.getDescription()));
    	                table.addCell(createCell(pr.getCurrentStock() != null ? pr.getCurrentStock().toString() : "0"));
    	                table.addCell(createCell(pr.getRequiredQty() != null ? pr.getRequiredQty().toString() : "0"));
    	                table.addCell(createCell(pr.getRequiredDate() != null ? pr.getRequiredDate().format(dateFormat) : "-"));
    	                table.addCell(createCell(pr.getRemark()));
    	                table.addCell(createCell(pr.getCreatedDate() != null ? pr.getCreatedDate().format(dtFormat) : "-"));
    	            }
    	        }

    	        // 7. Add table
    	        document.add(table);

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        try {
    	            response.reset();
    	            response.setContentType("text/plain");
    	            response.getWriter().write("Error generating PDF: " + e.getMessage());
    	        } catch (IOException ioEx) {
    	            ioEx.printStackTrace();
    	        }
    	    } finally {
    	        if (document != null && document.isOpen()) {
    	            document.close();
    	        }
    	        try {
    	            response.getOutputStream().flush();
    	        } catch (IOException ignored) {}
    	    }
    	}

    	// 🔹 Helper
    	private PdfPCell createCell(String text) {
    	    Font font = FontFactory.getFont(FontFactory.HELVETICA, 8);
    	    PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "-", font));
    	    cell.setPadding(4f);
    	    cell.setNoWrap(false);
    	    cell.setVerticalAlignment(Element.ALIGN_TOP);
    	    return cell;
    	}


    public String exportSelectedToPDFFile(List<Long> selectedIds) {
        try {
            // Create directory if not exists
            File folder = new File("D:/download");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Generate unique filename
            DateTimeFormatter fileFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String timestamp = LocalDateTime.now().format(fileFormatter);
            String filePath = "D:/download/purchase_requisitions_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Selected Purchase Requisitions Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            Stream.of("PR_Number","P_REQ_NO", "Part Number", "Description", "Current Stock",
                    "Required Qty", "Required Date", "Remark", "Created Date")
                .forEach(header -> {
                    PdfPCell cell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });

            // Fetch only selected IDs
            List<PurchaseRequisition> requisitions = repository.findAllById(selectedIds);

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (PurchaseRequisition pr : requisitions) {
                table.addCell(pr.getId().toString());
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

    public void exportToCSV(HttpServletResponse response) {
    	
    	try {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=purchase_requisitions.csv");

            PrintWriter writer = response.getWriter();

            // CSV header
            writer.println("PR_Number, P_REQ_NO, Part Number,Description,Current Stock,Required Qty,Required Date,Remark,Created Date");

            List<PurchaseRequisition> requisitions = repository.findByStatus("Open");

            for (PurchaseRequisition pr : requisitions) {
                writer.println(
                    escapeCSV(pr.getBatchNumber()) + "," +
                    escapeCSV(pr.getId().toString()) + "," +
                    escapeCSV(pr.getPartNumber()) + "," +
                    escapeCSV(pr.getDescription()) + "," +
                    pr.getCurrentStock() + "," +
                    pr.getRequiredQty() + "," +
                    pr.getRequiredDate() + "," +
                    escapeCSV(pr.getRemark()) + "," +
                    pr.getCreatedDate()
                );
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException("CSV generation failed", e);
        }
//        try {
//            // Ensure the folder exists
//            File folder = new File("D:/download");
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//
//            // Create unique filename
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
//            String timestamp = LocalDateTime.now().format(formatter);
//            String filePath = "D:/download/purchase_requisitions_" + timestamp + ".csv";
//
//            FileWriter writer = new FileWriter(filePath);
//
//            // CSV header
//            writer.write("ID,SR No,Part Number,Description,Current Stock,Required Qty,Required Date,Remark,Created Date\n");
//
//            List<PurchaseRequisition> requisitions = repository.findAll();
//
//            for (PurchaseRequisition pr : requisitions) {
//                writer.write(pr.getId() + "," +
//                        escapeCSV(pr.getPartNumber()) + "," +
//                        escapeCSV(pr.getDescription()) + "," +
//                        pr.getCurrentStock() + "," +
//                        pr.getRequiredQty() + "," +
//                        pr.getRequiredDate() + "," +
//                        escapeCSV(pr.getRemark()) + "," +
//                        pr.getCreatedDate() + "\n");
//            }
//
//            writer.close();
//            return "CSV saved at: " + filePath;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "CSV generation failed: " + e.getMessage();
//        }
    }

    
    public String exportSelectedToCSV(List<Long> selectedIds) {
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

            // Fetch only selected data
            List<PurchaseRequisition> requisitions = repository.findAllById(selectedIds);

            for (PurchaseRequisition pr : requisitions) {
                writer.write(pr.getId() + "," +
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