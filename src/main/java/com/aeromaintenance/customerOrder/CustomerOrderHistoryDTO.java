package com.aeromaintenance.customerOrder;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class CustomerOrderHistoryDTO {
    private String orderNo;
    private String srNo;
    private String customerName;
    private String partDesc;
    private String partNo;
    private Long qty;
    private String status;
    private Boolean workOrder;
    private String documentPath;
    private String makerUserName;
    private Timestamp makerDate;
    private String checkerUserName;
    private Timestamp checkerDate;
    private String userRole;
    private String userAction;
    private String remark;

    // ✅ Constructor to handle raw Object[] from native SQL
    public CustomerOrderHistoryDTO(Object orderNo, Object srNo, Object customerName,
                                   Object partDesc, Object partNo, Object qty,
                                   Object status, Object workOrder, Object documentPath,
                                   Object makerUserName, Object makerDate,
                                   Object checkerUserName, Object checkerDate,
                                   Object userRole, Object userAction, Object remark) {
        this.orderNo = orderNo != null ? orderNo.toString() : null;
        this.srNo = srNo != null ? srNo.toString() : null;
        this.customerName = customerName != null ? customerName.toString() : null;
        this.partDesc = partDesc != null ? partDesc.toString() : null;
        this.partNo = partNo != null ? partNo.toString() : null;

        // Convert BigInteger or Long to Long
        if (qty instanceof BigInteger) {
            this.qty = ((BigInteger) qty).longValue();
        } else if (qty instanceof Long) {
            this.qty = (Long) qty;
        } else {
            this.qty = null;
        }

        this.status = status != null ? status.toString() : null;

        // Convert workOrder field (Integer or Boolean) to Boolean
        if (workOrder instanceof Boolean) {
            this.workOrder = (Boolean) workOrder;
        } else if (workOrder instanceof Number) {
            this.workOrder = ((Number) workOrder).intValue() != 0;
        } else {
            this.workOrder = null;
        }

        this.documentPath = documentPath != null ? documentPath.toString() : null;
        this.makerUserName = makerUserName != null ? makerUserName.toString() : null;
        this.makerDate = makerDate != null ? (Timestamp) makerDate : null;
        this.checkerUserName = checkerUserName != null ? checkerUserName.toString() : null;
        this.checkerDate = checkerDate != null ? (Timestamp) checkerDate : null;
        this.userRole = userRole != null ? userRole.toString() : null;
        this.userAction = userAction != null ? userAction.toString() : null;
        this.remark = remark != null ? remark.toString() : null;
    }

    // (Optional: Add getters & setters if needed)
}
