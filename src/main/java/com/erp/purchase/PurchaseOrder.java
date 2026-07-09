package com.erp.purchase;

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
@Table(name = "purchase_order")
@Getter
@NoArgsConstructor
public class PurchaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Partner supplier;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PurchaseOrderStatus status;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderLine> lines = new ArrayList<>();

    public PurchaseOrder(String orderNo, Partner supplier, Warehouse warehouse, LocalDate orderDate) {
        this.orderNo = orderNo;
        this.supplier = supplier;
        this.warehouse = warehouse;
        this.orderDate = orderDate;
        this.status = PurchaseOrderStatus.DRAFT;
    }

    public void addLine(PurchaseOrderLine line) {
        this.lines.add(line);
        line.setPurchaseOrder(this);
    }
    public void submit() {
        if (this.status != PurchaseOrderStatus.DRAFT) {
            throw new IllegalStateException("작성 상태에서만 상신할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = PurchaseOrderStatus.SUBMITTED;
    }

    public void approve() {
        if (this.status != PurchaseOrderStatus.SUBMITTED) {
            throw new IllegalStateException("상신 상태에서만 승인할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = PurchaseOrderStatus.APPROVED;
    }

    public void reject() {
        if (this.status != PurchaseOrderStatus.SUBMITTED) {
            throw new IllegalStateException("상신 상태에서만 반려할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = PurchaseOrderStatus.REJECTED;
    }

    public void complete() {
        if (this.status != PurchaseOrderStatus.APPROVED) {
            throw new IllegalStateException("승인 상태에서만 완료할 수 있습니다. 현재 상태: " + this.status);
        }
        this.status = PurchaseOrderStatus.COMPLETED;
    }
}