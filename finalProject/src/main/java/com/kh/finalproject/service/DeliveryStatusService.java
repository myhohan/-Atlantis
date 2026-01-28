package com.kh.finalproject.service;

import java.util.List;
import com.kh.finalproject.dto.DeliveryStatus;

public interface DeliveryStatusService {
    
    // 운송장 검색
    DeliveryStatus searchItem(String postNo);
    
    // 전체 요청 건수
    int getTotalCount();
    
    // 상태별 건수 확인 (대기, 배송중, 완료 등)
    int getCountByStatus(String status);
    
    // 배송 목록 조회 (필터: 전체 or 특정상태)
    List<DeliveryStatus> getDeliveryList(String filterStatus);
}