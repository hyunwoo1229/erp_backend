package com.erp.purchase.service;

import com.erp.item.Item;
import com.erp.item.repository.ItemRepository;
import com.erp.partner.Partner;
import com.erp.partner.repository.PartnerRepository;
import com.erp.purchase.PurchaseOrder;
import com.erp.purchase.PurchaseOrderLine;
import com.erp.purchase.dto.PurchaseOrderCreateRequest;
import com.erp.purchase.dto.PurchaseOrderResponse;
import com.erp.purchase.repository.PurchaseOrderRepository;
import com.erp.warehouse.Warehouse;
import com.erp.warehouse.repository.WarehouseRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PartnerRepository partnerRepository;
    private final WarehouseRepository warehouseRepository;
    private final ItemRepository itemRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                PartnerRepository partnerRepository,
                                WarehouseRepository warehouseRepository,
                                ItemRepository itemRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.partnerRepository = partnerRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public PurchaseOrderResponse create(PurchaseOrderCreateRequest request) {
        Partner supplier = partnerRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("공급사가 존재하지 않습니다."));
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("창고가 존재하지 않습니다."));

        String orderNo = "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PurchaseOrder order = new PurchaseOrder(orderNo, supplier, warehouse, LocalDate.now());

        for (PurchaseOrderCreateRequest.Line lineReq : request.getLines()) {
            Item item = itemRepository.findById(lineReq.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("품목이 존재하지 않습니다: " + lineReq.getItemId()));
            order.addLine(new PurchaseOrderLine(item, lineReq.getQty(), lineReq.getUnitPrice()));
        }

        PurchaseOrder saved = purchaseOrderRepository.save(order);
        return new PurchaseOrderResponse(saved);
    }

    @Transactional
    public PurchaseOrderResponse submit(Long orderId) {
        PurchaseOrder order = findOrder(orderId);
        order.submit();
        return new PurchaseOrderResponse(order);
    }

    @Transactional
    public PurchaseOrderResponse approve(Long orderId) {
        PurchaseOrder order = findOrder(orderId);
        order.approve();
        return new PurchaseOrderResponse(order);
    }

    @Transactional
    public PurchaseOrderResponse reject(Long orderId) {
        PurchaseOrder order = findOrder(orderId);
        order.reject();
        return new PurchaseOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public PurchaseOrderResponse findById(Long orderId) {
        return new PurchaseOrderResponse(findOrder(orderId));
    }

    private PurchaseOrder findOrder(Long orderId) {
        return purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("발주서가 존재하지 않습니다."));
    }
}