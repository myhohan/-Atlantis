package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentManagement {
    
    
    private String memberId;
    private int totalPayment;        // 총 결제 금액
    private int totalPendingPayment; // 결제 대기 금액
    private int totalCompletePayment;// 결제 완료 금액
    
    
    private String paymentNo;        // 결제 번호
    private String paymentDate;      // 결제일
    private int amount;              // 금액
    private String status;           // 상태 (대기, 완료 등)
    private String paymentDuration;  // 기간 필터용
}