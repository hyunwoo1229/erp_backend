package com.erp.warehouse.controller;

import com.erp.warehouse.dto.WarehouseCreateRequest;
import com.erp.warehouse.dto.WarehouseResponse;
import com.erp.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public WarehouseResponse create(@Valid @RequestBody WarehouseCreateRequest request) {
        return warehouseService.create(request);
    }

    @GetMapping
    public List<WarehouseResponse> findAll() {
        return warehouseService.findAll();
    }
}