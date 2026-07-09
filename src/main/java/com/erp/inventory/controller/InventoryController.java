package com.erp.inventory.controller;

import com.erp.inventory.dto.StockMoveRequest;
import com.erp.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inbound")
    public void inbound(@Valid @RequestBody StockMoveRequest request) {
        inventoryService.inbound(request.getItemId(), request.getWarehouseId(), request.getQuantity());
    }

    @PostMapping("/outbound")
    public void outbound(@Valid @RequestBody StockMoveRequest request) {
        inventoryService.outbound(request.getItemId(), request.getWarehouseId(), request.getQuantity());
    }

    @PostMapping("/outbound-pessimistic")
    public void outboundPessimistic(@Valid @RequestBody StockMoveRequest request) {
        inventoryService.outboundPessimistic(request.getItemId(), request.getWarehouseId(), request.getQuantity());
    }
}