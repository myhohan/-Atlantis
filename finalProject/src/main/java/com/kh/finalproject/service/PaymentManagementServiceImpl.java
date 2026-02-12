package com.kh.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.PaymentManagement;
import com.kh.finalproject.mapper.PaymentManagementMapper;

@Service
@RequiredArgsConstructor
public class PaymentManagementServiceImpl implements PaymentManagementService {

    private final PaymentManagementMapper mapper; // PaymentMapper 주입

    @Override
    public List<DeliveryRequest> selectPaymentList() {
        return mapper.selectPaymentList();
    }

    @Override
    public List<PaymentManagement> selectPaymentStats() {
        // 1. DB에서 날짜별 데이터를 가져옴 (변수명 명확히)
        List<PaymentManagement> statsList = mapper.selectPaymentStats();

        // 데이터가 없을 경우를 대비한 안전장치
        if (statsList == null || statsList.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. 누적 금액 계산 로직
        int cumulativeSum = 0;
        for (PaymentManagement stat : statsList) {
            // 당일 매출 합산
            cumulativeSum += stat.getAmount();
            
            // DTO의 필드에 누적액 세팅
            stat.setTotalCompletePayment(cumulativeSum);
            
            // 로그로 계산 결과 확인 (디버깅용)
            // log.info("날짜: {}, 당일: {}, 누적: {}", stat.getPaymentDate(), stat.getAmount(), cumulativeSum);
        }

        // 3. [중요] 가공된 리스트를 반환!
        return statsList; 
    }

	@Override
	public List<PaymentManagement> todayPaymentStats() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}