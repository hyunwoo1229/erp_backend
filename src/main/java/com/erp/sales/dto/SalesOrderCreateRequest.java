package com.erp.sales.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

@Getter
public class SalesOrderCreateRequest {

    @NotNull(message = "고객 ID는 필수입니다.")
    private Long customerId;

    @NotNull(message = "창고 ID는 필수입니다.")
    private Long warehouseId;

    @Valid
    @NotEmpty(message = "수주 품목은 최소 1개 이상이어야 합니다.")
    private List<Line> lines;

    @Getter
    public static class Line {
        @NotNull(message = "품목 ID는 필수입니다.")
        private Long itemId;

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        private int qty;

        @NotNull(message = "단가는 필수입니다.")
        private BigDecimal unitPrice;
    }
}