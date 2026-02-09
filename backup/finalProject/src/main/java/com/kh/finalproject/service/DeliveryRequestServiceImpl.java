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
@RequiredArgsConstructor // Mapper 자동 주입
@Transactional(rollbackFor = Exception.class) // 에러 발생 시 전체 롤백
public class DeliveryRequestServiceImpl implements DeliveryRequestService {

    private final DeliveryRequestMapper mapper;

    @Override
    public int deliveryRequest(List<DeliveryRequest> requestList) {
        int successCount = 0;
        
        // 리스트에 있는 모든 요청을 하나씩 Insert
        for (DeliveryRequest req : requestList) {
            log.info("택배 접수 처리 중: 보내는 분 - {}", req.getSenderName());
            
            int result = mapper.insertDeliveryRequest(req);
            successCount += result;
        }
        
        return successCount; // 총 저장된 건수 반환
    }
}