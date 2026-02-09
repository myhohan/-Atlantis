package com.kh.finalproject.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.DeliveryStatus;

@Mapper
public interface DeliveryStatusMapper {

    // 운송장 번호로 검색 (단건 조회)
    DeliveryStatus selectDeliveryByPostNo(String postNo);
    
    // 전체 배송 요청 건수
    int selectTotalCount();
    
    // 상태별 건수 조회 (접수대기, 배송중, 배송완료 등)
    int selectCountByStatus(String status);
    
    // 상태별 목록 조회 (필터링 기능)
    List<DeliveryStatus> selectListByStatus(String status);

    // 전체 목록 조회
    List<DeliveryStatus> selectAllList();
}
