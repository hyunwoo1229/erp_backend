package com.erp.inventory;

import com.erp.common.BaseEntity;
import com.erp.item.Item;
import com.erp.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "stock",
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "warehouse_id"})
)
@Getter
@NoArgsConstructor
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private int quantity;

    @Version
    private Long version;

    public Stock(Item item, Warehouse warehouse, int quantity) {
        this.item = item;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    public void increase(int amount) {
        this.quantity += amount;
    }

    public void decrease(int amount) {
        if (this.quantity < amount) {
            throw new IllegalArgumentException(
                    "재고가 부족합니다. 현재고: " + this.quantity + ", 요청: " + amount);
        }
        this.quantity -= amount;
    }
}