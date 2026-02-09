package com.kh.finalproject.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.mapper.PaymentManagementMapper;

@Service
@RequiredArgsConstructor
public class PaymentManagementServiceImpl implements PaymentManagementService {

    private final PaymentManagementMapper mapper; // PaymentMapper 주입

    @Override
    public List<DeliveryRequest> selectPaymentList() {
        return mapper.selectPaymentList();
    }
}