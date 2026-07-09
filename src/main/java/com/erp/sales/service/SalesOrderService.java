package com.erp.sales.service;

import com.erp.inventory.service.InventoryService;
import com.erp.item.Item;
import com.erp.item.repository.ItemRepository;
import com.erp.partner.Partner;
import com.erp.partner.repository.PartnerRepository;
import com.erp.sales.SalesOrder;
import com.erp.sales.SalesOrderLine;
import com.erp.sales.dto.SalesOrderCreateRequest;
import com.erp.sales.dto.SalesOrderResponse;
import com.erp.sales.repository.SalesOrderRepository;
import com.erp.warehouse.Warehouse;
import com.erp.warehouse.repository.WarehouseRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final PartnerRepository partnerRepository;
    private final WarehouseRepository warehouseRepository;
    private final ItemRepository itemRepository;
    private final InventoryService inventoryService;

    public SalesOrderService(SalesOrderRepository salesOrderRepository,
                             PartnerRepository partnerRepository,
                             WarehouseRepository warehouseRepository,
                             ItemRepository itemRepository,
                             InventoryService inventoryService) {
        this.salesOrderRepository = salesOrderRepository;
        this.partnerRepository = partnerRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemRepository = itemRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public SalesOrderResponse create(SalesOrderCreateRequest request) {
        Partner customer = partnerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("창고가 존재하지 않습니다."));

        String orderNo = "SO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        SalesOrder order = new SalesOrder(orderNo, customer, warehouse, LocalDate.now());

        for (SalesOrderCreateRequest.Line lineReq : request.getLines()) {
            Item item = itemRepository.findById(lineReq.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("품목이 존재하지 않습니다: " + lineReq.getItemId()));
            order.addLine(new SalesOrderLine(item, lineReq.getQty(), lineReq.getUnitPrice()));
        }

        SalesOrder saved = salesOrderRepository.save(order);
        return new SalesOrderResponse(saved);
    }

    @Transactional
    public SalesOrderResponse confirm(Long orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("수주가 존재하지 않습니다."));

        order.confirm();

        for (SalesOrderLine line : order.getLines()) {
            inventoryService.outboundPessimistic(
                    line.getItem().getId(),
                    order.getWarehouse().getId(),
                    line.getQty());
        }

        return new SalesOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public SalesOrderResponse findById(Long orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("수주가 존재하지 않습니다."));
        return new SalesOrderResponse(order);
    }
}