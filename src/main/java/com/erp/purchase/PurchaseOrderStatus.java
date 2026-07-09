package com.erp.purchase;

public enum PurchaseOrderStatus {
    DRAFT,      // 작성
    SUBMITTED,  // 상신 (승인 요청)
    APPROVED,   // 승인
    REJECTED,   // 반려
    COMPLETED   // 완료 (입고 처리됨)
}