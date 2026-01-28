package com.kh.finalproject.service;

import com.kh.finalproject.dto.DeliveryLostandFound;

public interface DeliveryLostandFoundService {

    // 전체 리포트 개수
    int getTotalReportCount();

    // 조사중 개수
    int getInvestigationCount();

    // 발견 개수
    int getDiscoverCount();

    // 신고 접수 기능
    String fileReport(DeliveryLostandFound dto);
}
