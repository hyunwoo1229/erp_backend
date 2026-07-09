package com.erp.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StockMoveRequest {

    @NotNull(message = "품목 ID는 필수입니다.")
    private Long itemId;

    @NotNull(message = "창고 ID는 필수입니다.")
    private Long warehouseId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}