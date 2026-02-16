package com.aeromaintenance.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private EntityManager entityManager;

    // Only map entity names to their class paths - NO COLUMN MAPPING NEEDED
    private static final Map<String, String> ENTITY_MAPPING = new HashMap<>();

    static {
        // Just add your entity class paths here - columns are auto-discovered
        ENTITY_MAPPING.put("Product", "com.aeromaintenance.product.Product");
        ENTITY_MAPPING.put("Inventory", "com.aeromaintenance.store.inventory.StoreInventory");
        ENTITY_MAPPING.put("WorkOrder", "com.aeromaintenance.WorkOrder.WorkOrder");
        ENTITY_MAPPING.put("PurchaseOrder", "com.aeromaintenance.PurchaseOrder.PurchaseOrder");
        ENTITY_MAPPING.put("Supplier", "com.aeromaintenance.supplier.SupplierModel");
        ENTITY_MAPPING.put("CustomerOrder", "com.aeromaintenance.customerOrder.CustomerOrder");
        ENTITY_MAPPING.put("CustomerReg", "com.aeromaintenance.CustomerReg.Customer");
        ENTITY_MAPPING.put("CustomerRepairProduct", "com.aeromaintenance.CustomerRepairProduct.CustomerRepairProduct");
        ENTITY_MAPPING.put("DispatchReport", "com.aeromaintenance.DispatchReport.DispatchReport");
        ENTITY_MAPPING.put("InspectionReport", "com.aeromaintenance.inspectionReport.InspectionReport");
        ENTITY_MAPPING.put("MaterialReceiptNote", "com.aeromaintenance.MaterialReceiptNote.MaterialReceiptNote");
        ENTITY_MAPPING.put("MaterialRequisition", "com.aeromaintenance.MaterialRequisition.MaterialRequisition");
        ENTITY_MAPPING.put("PurchaseRequisition", "com.aeromaintenance.PurchaseRequisition.PurchaseRequisition");
        ENTITY_MAPPING.put("storeAcceptance", "com.aeromaintenance.storeAcceptance.StoreAcc");
        
    }

    /**
     * Automatically get all available columns for any entity using reflection
     * NO MANUAL MAPPING REQUIRED!
     */
    public Map<String, String> getAvailableColumns(String entityName) {
        try {
            String entityClassName = ENTITY_MAPPING.get(entityName);
            if (entityClassName == null) {
                throw new IllegalArgumentException("Invalid entity name: " + entityName);
            }

            Class<?> entityClass = Class.forName(entityClassName);
            Map<String, String> columns = new LinkedHashMap<>();

            // Get all fields from the entity class
            Field[] fields = entityClass.getDeclaredFields();

            for (Field field : fields) {
                // Skip transient fields and version fields
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }

                String fieldName = field.getName();

                // Convert camelCase to Title Case for display
                String displayName = convertToDisplayName(fieldName);

                columns.put(fieldName, displayName);
            }

            return columns;

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Entity class not found: " + entityName);
        }
    }

    /**
     * Generate dynamic report based on user selection
     */
    public List<Map<String, Object>> generateReport(ReportRequest request) {
        String entityClassName = ENTITY_MAPPING.get(request.getEntityName());

        if (entityClassName == null) {
            throw new IllegalArgumentException("Invalid entity name: " + request.getEntityName());
        }

        // Validate that requested columns exist in the entity
        validateColumns(request.getEntityName(), request.getColumns());

        // Build dynamic JPQL query
        String jpql = buildDynamicQuery(request);

        Query query = entityManager.createQuery(jpql);

        // Apply filters if present
        applyFilters(query, request.getFilter());

        List<?> results = query.getResultList();

        // Transform results to Map format
        return transformResults(results, request.getColumns());
    }

    /**
     * Build JPQL query dynamically based on selected columns
     */
    private String buildDynamicQuery(ReportRequest request) {
        String entityClassName = ENTITY_MAPPING.get(request.getEntityName());
        String simpleClassName = entityClassName.substring(entityClassName.lastIndexOf('.') + 1);
        String entityAlias = simpleClassName.toLowerCase().charAt(0) + "";

        StringBuilder queryBuilder = new StringBuilder("SELECT ");

        // Build column selection
        if (request.getColumns() == null || request.getColumns().isEmpty()) {
            // If no columns specified, select the entity itself
            queryBuilder.append(entityAlias);
        } else {
            // Add selected columns
            List<String> columnSelects = request.getColumns().stream()
                    .map(col -> entityAlias + "." + col)
                    .collect(Collectors.toList());

            queryBuilder.append(String.join(", ", columnSelects));
        }

        queryBuilder.append(" FROM ").append(simpleClassName).append(" ").append(entityAlias);

        // Add WHERE clause for filters
        List<String> conditions = new ArrayList<>();

        if (request.getFilter() != null) {
            if (request.getFilter().getFlag() != null) {
                conditions.add(entityAlias + ".Rflag = :Rflag");
            }

            if (request.getFilter().getDateFrom() != null && request.getFilter().getDateTo() != null) {
                // Use dateField from filter if specified, otherwise default to "registrationDate"
                String dateField = request.getFilter().getDateField();
                if (dateField == null || dateField.isEmpty()) {
                    dateField = "registrationDate"; // Default date field
                }
                conditions.add(entityAlias + "." + dateField + " BETWEEN :dateFrom AND :dateTo");
            }
        }

        if (!conditions.isEmpty()) {
            queryBuilder.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        return queryBuilder.toString();
    }

    /**
     * Apply filter parameters to query
     */
    private void applyFilters(Query query, ReportRequest.ReportFilter filter) {
        if (filter == null) return;

        if (filter.getFlag() != null) {
            query.setParameter("flag", filter.getFlag());
        }

        // Apply date range filter
        if (filter.getDateFrom() != null && filter.getDateTo() != null) {
            try {
                // Parse date strings to java.util.Date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date dateFrom = sdf.parse(filter.getDateFrom());
                java.util.Date dateTo = sdf.parse(filter.getDateTo());

                query.setParameter("dateFrom", dateFrom);
                query.setParameter("dateTo", dateTo);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd format.");
            }
        }
    }

    /**
     * Transform query results to List of Maps
     */
    private List<Map<String, Object>> transformResults(List<?> results, List<String> columns) {
        List<Map<String, Object>> transformedResults = new ArrayList<>();

        for (Object result : results) {
            Map<String, Object> recordMap = new LinkedHashMap<>();

            if (result instanceof Object[]) {
                // Multiple columns selected
                Object[] row = (Object[]) result;
                for (int i = 0; i < columns.size() && i < row.length; i++) {
                    recordMap.put(columns.get(i), row[i]);
                }
            } else {
                // Single column or entire entity selected
                if (columns.size() == 1) {
                    recordMap.put(columns.get(0), result);
                } else {
                    // Entity object - extract all requested fields using reflection
                    for (String column : columns) {
                        try {
                            Field field = result.getClass().getDeclaredField(column);
                            field.setAccessible(true);
                            recordMap.put(column, field.get(result));
                        } catch (Exception e) {
                            recordMap.put(column, null);
                        }
                    }
                }
            }

            transformedResults.add(recordMap);
        }

        return transformedResults;
    }

    /**
     * Validate if requested columns exist in entity using reflection
     */
    private void validateColumns(String entityName, List<String> columns) {
        try {
            String entityClassName = ENTITY_MAPPING.get(entityName);
            Class<?> entityClass = Class.forName(entityClassName);

            Set<String> validColumns = new HashSet<>();
            for (Field field : entityClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Transient.class)) {
                    validColumns.add(field.getName());
                }
            }

            for (String column : columns) {
                if (!validColumns.contains(column)) {
                    throw new IllegalArgumentException(
                            "Invalid column '" + column + "' for entity: " + entityName +
                                    ". Available columns: " + validColumns
                    );
                }
            }

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Entity class not found: " + entityName);
        }
    }

    /**
     * Convert camelCase to Title Case
     * Example: productName -> Product Name
     */
    private String convertToDisplayName(String fieldName) {
        // Handle special cases
        if (fieldName.equals("id")) return "ID";
        if (fieldName.equals("oem")) return "OEM";
        if (fieldName.equals("nha")) return "NHA";
        if (fieldName.equals("cmm")) return "CMM";

        // Convert camelCase to Title Case
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);

            if (i == 0) {
                result.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                result.append(' ').append(c);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Get all available entities
     */
    public Set<String> getAvailableEntities() {
        return ENTITY_MAPPING.keySet();
    }

    /**
     * Get detailed entity information including table name and column count
     */
    public Map<String, Object> getEntityInfo(String entityName) {
        try {
            String entityClassName = ENTITY_MAPPING.get(entityName);
            if (entityClassName == null) {
                throw new IllegalArgumentException("Invalid entity name: " + entityName);
            }

            Class<?> entityClass = Class.forName(entityClassName);

            Map<String, Object> info = new HashMap<>();
            info.put("entityName", entityName);
            info.put("className", entityClassName);

            // Get table name from @Table annotation
            if (entityClass.isAnnotationPresent(Table.class)) {
                Table tableAnnotation = entityClass.getAnnotation(Table.class);
                info.put("tableName", tableAnnotation.name());
            }

            // Count columns
            Map<String, String> columns = getAvailableColumns(entityName);
            info.put("columnCount", columns.size());
            info.put("columns", columns);

            return info;

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Entity class not found: " + entityName);
        }
    }
}