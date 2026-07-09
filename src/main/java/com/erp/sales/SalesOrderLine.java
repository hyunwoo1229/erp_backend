package com.erp.sales;

import com.erp.common.BaseEntity;
import com.erp.item.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sales_order_line")
@Getter
@NoArgsConstructor
public class SalesOrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_order_id", nullable = false)
    @Setter
    private SalesOrder salesOrder;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private int qty;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    public SalesOrderLine(Item item, int qty, BigDecimal unitPrice) {
        this.item = item;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}