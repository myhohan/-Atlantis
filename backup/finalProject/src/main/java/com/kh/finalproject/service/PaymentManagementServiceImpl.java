package com.kh.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.PaymentManagement;
import com.kh.finalproject.mapper.PaymentManagementMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentManagementServiceImpl implements PaymentManagementService {
    
    private final PaymentManagementMapper mapper;
    
    @Override
    public int getTotalPayment(String memberId) {
        // 결과가 null이면 0 반환
        Integer result = mapper.selectTotalPayment(memberId);
        return (result != null) ? result : 0;
    }

    @Override
    public int getPendingPayment(String memberId) {
        Integer result = mapper.selectPendingPayment(memberId);
        return (result != null) ? result : 0;
    }
    
    @Override
    public int getCompletePayment(String memberId) {
        Integer result = mapper.selectCompletePayment(memberId);
        return (result != null) ? result : 0;
    }

    @Override
    public List<PaymentManagement> getPaymentList(String memberId, String duration) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        
        // duration 값이 없으면 '전체'로 간주
        if(duration == null || duration.isEmpty()) {
            params.put("duration", "전체");
        } else {
            params.put("duration", duration);
        }
        
        return mapper.selectPaymentList(params);
    }
}