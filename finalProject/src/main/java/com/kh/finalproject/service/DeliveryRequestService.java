package com.kh.finalproject.service;

import java.util.List;
import com.kh.finalproject.dto.DeliveryRequest;

public interface DeliveryRequestService {
    
    /**
     * 택배 접수 요청 (여러 건 동시 처리를 위해 List로 받음)
     * @param requestList
     * @return 성공한 건수
     */
    int deliveryRequest(List<DeliveryRequest> requestList);

}