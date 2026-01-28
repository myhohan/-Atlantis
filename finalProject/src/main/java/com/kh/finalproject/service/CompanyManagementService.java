package com.kh.finalproject.service;

import java.util.Map;
import com.kh.finalproject.dto.CompanyManagement;

public interface CompanyManagementService {

    // 전체 배송 건수
    int getTotalDelivery();

    // 활성 기사 수 확인
    int getActivePersonnel(String status);

    // 고객 만족도
    int getCustomerSatisfaction();

    // 최근 배송 활동
    String getRecentDeliveryActivity(String postNo);

    // 배송기사 관리
    Map<String, Object> getPersonnelManagement(String personnelName);

    // 현재 배송 상태
    String getCurrentStatus(String postNo);
    
    // 리포트 분석
    CompanyManagement getReportAnalysis(String postNo);
}