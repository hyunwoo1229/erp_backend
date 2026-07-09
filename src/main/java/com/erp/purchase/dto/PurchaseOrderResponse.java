package com.erp.purchase.dto;

import com.erp.purchase.PurchaseOrder;
import com.erp.purchase.PurchaseOrderStatus;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class PurchaseOrderResponse {

    private final Long id;
    private final String orderNo;
    private final String supplierName;
    private final String warehouseName;
    private final PurchaseOrderStatus status;
    private final LocalDate orderDate;

    public PurchaseOrderResponse(PurchaseOrder order) {
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.supplierName = order.getSupplier().getName();
        this.warehouseName = order.getWarehouse().getName();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }
}