package com.aeromaintenance.customerOrder;

import lombok.Data;

@Data

public class CustomerOrderShortDTO {
    private String orderNo;
//    private String srNo;
    private String customerName;
    private String partDesc;
    private String partNo;
    private Long qty;


    public CustomerOrderShortDTO(String orderNo, String customerName,
                                 String partDesc, String partNo, Long qty) {
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.partDesc = partDesc;
        this.partNo = partNo;
        this.qty = qty;
    }


}
