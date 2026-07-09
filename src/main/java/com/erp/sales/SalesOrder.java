package com.erp.sales;

import com.erp.common.BaseEntity;
import com.erp.partner.Partner;
import com.erp.warehouse.Warehouse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales_order")
@Getter
@NoArgsConstructor
public class SalesOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Partner customer;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SalesOrderStatus status;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesOrderLine> lines = new ArrayList<>();

    public SalesOrder(String orderNo, Partner customer, Warehouse warehouse, LocalDate orderDate) {
        this.orderNo = orderNo;
        this.customer = customer;
        this.warehouse = warehouse;
        this.orderDate = orderDate;
        this.status = SalesOrderStatus.DRAFT;
    }

    public void addLine(SalesOrderLine line) {
        this.lines.add(line);
        line.setSalesOrder(this);
    }

    public void confirm() {
        if (this.status != SalesOrderStatus.DRAFT) {
            throw new IllegalStateException("작성 상태에서만 확정할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = SalesOrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status != SalesOrderStatus.DRAFT) {
            throw new IllegalStateException("작성 상태에서만 취소할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = SalesOrderStatus.CANCELLED;
    }
}