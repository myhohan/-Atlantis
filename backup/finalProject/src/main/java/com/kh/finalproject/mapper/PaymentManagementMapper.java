package com.kh.finalproject.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.PaymentManagement;

@Mapper
public interface PaymentManagementMapper {
    
    // 총 결제 금액 조회
    Integer selectTotalPayment(String memberId);
    
    // 결제 대기 금액 조회
    Integer selectPendingPayment(String memberId);
    
    // 결제 완료 금액 조회
    Integer selectCompletePayment(String memberId);
    
    // 기간별/조건별 결제 목록 조회
    List<PaymentManagement> selectPaymentList(Map<String, Object> params);
}
