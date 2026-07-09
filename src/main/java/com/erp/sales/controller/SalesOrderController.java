package com.erp.sales.controller;

import com.erp.sales.dto.SalesOrderCreateRequest;
import com.erp.sales.dto.SalesOrderResponse;
import com.erp.sales.service.SalesOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sales-orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    public SalesOrderResponse create(@Valid @RequestBody SalesOrderCreateRequest request) {
        return salesOrderService.create(request);
    }

    @GetMapping("/{id}")
    public SalesOrderResponse findById(@PathVariable Long id) {
        return salesOrderService.findById(id);
    }

    @PostMapping("/{id}/confirm")
    public SalesOrderResponse confirm(@PathVariable Long id) {
        return salesOrderService.confirm(id);
    }
}