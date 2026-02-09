package com.kh.finalproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.DeliveryRequest; // DTO 임포트 필수

@Mapper
public interface PaymentManagementMapper {
    // 반환 타입은 무조건 List<DeliveryRequest> 여야 합니다.
    List<DeliveryRequest> selectPaymentList();
}
