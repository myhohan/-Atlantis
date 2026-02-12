package com.kh.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.mapper.DeliveryRequestMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional // [중요] 예외 발생 시 자동 롤백, 성공 시 자동 커밋
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final DeliveryRequestMapper mapper;

    @Override
    public String deliveryRequest(List<DeliveryRequest> requestList) { 
        
        StringBuilder sb = new StringBuilder();
        int successCount = 0;
        
        for (DeliveryRequest req : requestList) {
            
            int result = mapper.insertDeliveryRequest(req);
            
            if(result > 0) {
                successCount++;
               
                if(sb.length() > 0) sb.append(", ");
                sb.append(req.getPostNo()); 
            }
        }
        
        if(successCount > 0) {
            return "접수 완료! 송장번호: [" + sb.toString() + "]";
        } else {
            return "접수 실패";
        }
    }

	@Override
	public List<DeliveryRequest> selectPaymentList() {
		
		return null;
	}
}