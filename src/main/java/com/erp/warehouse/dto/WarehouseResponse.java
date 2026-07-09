package com.erp.warehouse.dto;

import com.erp.warehouse.Warehouse;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class WarehouseResponse {

    private final Long id;
    private final String code;
    private final String name;
    private final LocalDateTime createdAt;

    public WarehouseResponse(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.code = warehouse.getCode();
        this.name = warehouse.getName();
        this.createdAt = warehouse.getCreatedAt();
    }
}