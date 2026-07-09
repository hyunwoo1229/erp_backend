package com.erp.inventory.repository;

import com.erp.inventory.Stock;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByItemIdAndWarehouseId(Long itemId, Long warehouseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.item.id = :itemId and s.warehouse.id = :warehouseId")
    Optional<Stock> findByItemIdAndWarehouseIdForUpdate(Long itemId, Long warehouseId);
}