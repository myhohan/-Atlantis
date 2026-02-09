package com.kh.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.DeliveryRequest;

@Mapper
public interface DeliveryRequestMapper {

    // 택배 접수 (DB 저장)
    int insertDeliveryRequest(DeliveryRequest request);
    
    List<DeliveryRequest> selectPaymentList();
}