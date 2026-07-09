package com.erp.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WarehouseCreateRequest {

    @NotBlank(message = "창고 코드는 필수입니다.")
    private String code;

    @NotBlank(message = "창고명은 필수입니다.")
    private String name;
}