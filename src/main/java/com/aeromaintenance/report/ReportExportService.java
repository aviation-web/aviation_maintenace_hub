package com.aeromaintenance.report;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReportExportService {

    /**
     * Export report data to CSV format
     */
    public ByteArrayOutputStream exportToCSV(List<Map<String, Object>> data, List<String> columns) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Write header
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(String.join(",", columns)).append("\n");

        // Write data rows
        for (Map<String, Object> row : data) {
            StringBuilder rowData = new StringBuilder();

            for (int i = 0; i < columns.size(); i++) {
                String column = columns.get(i);
                Object value = row.get(column);

                String cellValue = formatCellValue(value);

                // Escape commas and quotes in CSV
                if (cellValue.contains(",") || cellValue.contains("\"")) {
                    cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                }

                rowData.append(cellValue);

                if (i < columns.size() - 1) {
                    rowData.append(",");
                }
            }

            csvContent.append(rowData).append("\n");
        }

        outputStream.write(csvContent.toString().getBytes());
        return outputStream;
    }

    /**
     * Export report data to Excel format
     */
    public ByteArrayOutputStream exportToExcel(List<Map<String, Object>> data, List<String> columns, String sheetName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Create header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        // Create data style
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        // Create date style
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.cloneStyleFrom(dataStyle);
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(formatColumnName(columns.get(i)));
            cell.setCellStyle(headerStyle);
        }

        // Create data rows
        int rowNum = 1;
        for (Map<String, Object> record : data) {
            Row row = sheet.createRow(rowNum++);

            for (int i = 0; i < columns.size(); i++) {
                Cell cell = row.createCell(i);
                Object value = record.get(columns.get(i));

                if (value == null) {
                    cell.setCellValue("");
                } else if (value instanceof Number) {
                    cell.setCellValue(((Number) value).doubleValue());
                } else if (value instanceof Date) {
                    cell.setCellValue((Date) value);
                    cell.setCellStyle(dateStyle);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else {
                    cell.setCellValue(value.toString());
                }

                if (!(value instanceof Date)) {
                    cell.setCellStyle(dataStyle);
                }
            }
        }

        // Auto-size columns
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }

    /**
     * Format cell value for CSV export
     */
    private String formatCellValue(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format((Date) value);
        }

        return value.toString();
    }

    /**
     * Format column name for display (convert camelCase to Title Case)
     */
    private String formatColumnName(String columnName) {
        // Convert camelCase to Title Case
        String result = columnName.replaceAll("([A-Z])", " $1");
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}