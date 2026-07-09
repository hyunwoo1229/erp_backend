package com.erp.purchase.controller;

import com.erp.purchase.dto.PurchaseOrderCreateRequest;
import com.erp.purchase.dto.PurchaseOrderResponse;
import com.erp.purchase.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public PurchaseOrderResponse create(@Valid @RequestBody PurchaseOrderCreateRequest request) {
        return purchaseOrderService.create(request);
    }

    @GetMapping("/{id}")
    public PurchaseOrderResponse findById(@PathVariable Long id) {
        return purchaseOrderService.findById(id);
    }

    @PostMapping("/{id}/submit")
    public PurchaseOrderResponse submit(@PathVariable Long id) {
        return purchaseOrderService.submit(id);
    }

    @PostMapping("/{id}/approve")
    public PurchaseOrderResponse approve(@PathVariable Long id) {
        return purchaseOrderService.approve(id);
    }

    @PostMapping("/{id}/reject")
    public PurchaseOrderResponse reject(@PathVariable Long id) {
        return purchaseOrderService.reject(id);
    }
}