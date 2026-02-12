package com.kh.finalproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.finalproject.dto.DeliveryStatus;

@Mapper
public interface DeliveryStatusMapper {

    // 기존 메서드들...
    DeliveryStatus selectDeliveryByPostNo(String postNo);
    int selectTotalCount();
    int selectCountByStatus(String status);
    List<DeliveryStatus> selectAllList();
    List<DeliveryStatus> selectListByStatus(String status);

    // ▼▼▼ [새로 추가해야 할 메서드 2개] ▼▼▼
    
    // 1. 전체 게시글 수 조회 (검색 조건 포함)
    int getListCount(Map<String, Object> paramMap);

    // 2. 목록 조회 (검색 조건 + 페이지네이션 포함)
    // RowBounds는 MyBatis가 알아서 처리하므로 XML에는 파라미터로 안 써도 됨
    List<DeliveryStatus> selectDeliveryList(Map<String, Object> paramMap);

    List<DeliveryStatus> getDeliveryStatusCounts();
}
