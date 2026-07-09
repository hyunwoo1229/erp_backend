package com.erp.item.dto;

import com.erp.item.Item;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ItemResponse {

    private final Long id;
    private final String code;
    private final String name;
    private final String unit;
    private final int safetyStock;
    private final LocalDateTime createdAt;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.code = item.getCode();
        this.name = item.getName();
        this.unit = item.getUnit();
        this.safetyStock = item.getSafetyStock();
        this.createdAt = item.getCreatedAt();
    }
}