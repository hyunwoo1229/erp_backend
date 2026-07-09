package com.erp.sales.dto;

import com.erp.sales.SalesOrder;
import com.erp.sales.SalesOrderStatus;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SalesOrderResponse {

    private final Long id;
    private final String orderNo;
    private final String customerName;
    private final String warehouseName;
    private final SalesOrderStatus status;
    private final LocalDate orderDate;

    public SalesOrderResponse(SalesOrder order) {
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.customerName = order.getCustomer().getName();
        this.warehouseName = order.getWarehouse().getName();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }
}