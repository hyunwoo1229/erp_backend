package com.erp.inventory.service;

import com.erp.inventory.Stock;
import com.erp.inventory.StockTransaction;
import com.erp.inventory.TransactionType;
import com.erp.inventory.repository.StockRepository;
import com.erp.inventory.repository.StockTransactionRepository;
import com.erp.item.Item;
import com.erp.item.repository.ItemRepository;
import com.erp.warehouse.Warehouse;
import com.erp.warehouse.repository.WarehouseRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final StockRepository stockRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryService(StockRepository stockRepository,
                            StockTransactionRepository stockTransactionRepository,
                            ItemRepository itemRepository,
                            WarehouseRepository warehouseRepository) {
        this.stockRepository = stockRepository;
        this.stockTransactionRepository = stockTransactionRepository;
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public void inbound(Long itemId, Long warehouseId, int quantity) {
        Stock stock = stockRepository.findByItemIdAndWarehouseId(itemId, warehouseId)
                .orElseGet(() -> createNewStock(itemId, warehouseId));

        stock.increase(quantity);

        StockTransaction tx = new StockTransaction(
                stock.getItem(), stock.getWarehouse(),
                TransactionType.IN, quantity, stock.getQuantity());
        stockTransactionRepository.save(tx);
    }


    @Retryable(
            retryFor = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 10,
            backoff = @Backoff(delay = 50)
    )
    @Transactional
    public void outbound(Long itemId, Long warehouseId, int quantity) {
        Stock stock = stockRepository.findByItemIdAndWarehouseId(itemId, warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("재고가 존재하지 않습니다."));

        stock.decrease(quantity);

        StockTransaction tx = new StockTransaction(
                stock.getItem(), stock.getWarehouse(),
                TransactionType.OUT, quantity, stock.getQuantity());
        stockTransactionRepository.save(tx);
    }

    private Stock createNewStock(Long itemId, Long warehouseId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("품목이 존재하지 않습니다."));
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("창고가 존재하지 않습니다."));
        return stockRepository.save(new Stock(item, warehouse, 0));
    }

    @Transactional
    public void outboundPessimistic(Long itemId, Long warehouseId, int quantity) {
        Stock stock = stockRepository.findByItemIdAndWarehouseIdForUpdate(itemId, warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("재고가 존재하지 않습니다."));

        stock.decrease(quantity);

        StockTransaction tx = new StockTransaction(
                stock.getItem(), stock.getWarehouse(),
                TransactionType.OUT, quantity, stock.getQuantity());
        stockTransactionRepository.save(tx);
    }
}