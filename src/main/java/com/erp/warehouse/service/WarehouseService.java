package com.erp.warehouse.service;

import com.erp.warehouse.Warehouse;
import com.erp.warehouse.dto.WarehouseCreateRequest;
import com.erp.warehouse.dto.WarehouseResponse;
import com.erp.warehouse.repository.WarehouseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public WarehouseResponse create(WarehouseCreateRequest request) {
        if (warehouseRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("이미 존재하는 창고 코드입니다: " + request.getCode());
        }
        Warehouse warehouse = new Warehouse(request.getCode(), request.getName());
        Warehouse saved = warehouseRepository.save(warehouse);
        return new WarehouseResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WarehouseResponse> findAll() {
        return warehouseRepository.findAll().stream()
                .map(WarehouseResponse::new)
                .toList();
    }
}