package com.erp.warehouse.repository;

import com.erp.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    boolean existsByCode(String code);
}