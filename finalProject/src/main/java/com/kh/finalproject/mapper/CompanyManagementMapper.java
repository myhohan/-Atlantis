package com.kh.finalproject.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.CompanyManagement;

@Mapper
public interface CompanyManagementMapper {
    
    // 전체 배송 건수 조회
    int selectTotalDeliveryCount();
    
    // 활성 기사 수 조회 (status 기준)
    int selectActivePersonnelCount(String status);
    
    // 고객 만족도 조회
    int selectCustomerSatisfaction();
    
    // 최근 배송 활동 조회
    String selectRecentDeliveryActivity(String postNo);

    // 기사 관리 정보 조회
    Map<String, Object> selectPersonnelManagement(String personnelName);

    // 현재 배송 상태 조회
    String selectCurrentStatus(String postNo);

    // 리포트 분석 데이터 조회
    CompanyManagement selectReportAnalysis(String postNo);
}


