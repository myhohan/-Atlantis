package com.kh.finalproject.service;

import java.util.List;
import java.util.Map;
import com.kh.finalproject.dto.PaymentManagement;

public interface PaymentManagementService {

    // 총 결제 금액
    int getTotalPayment(String memberId);
    
    // 결제 대기 금액
    int getPendingPayment(String memberId);
    
    // 결제 완료 금액
    int getCompletePayment(String memberId);
    
    // 결제 목록 (기간 필터 포함)
    List<PaymentManagement> getPaymentList(String memberId, String duration);
}