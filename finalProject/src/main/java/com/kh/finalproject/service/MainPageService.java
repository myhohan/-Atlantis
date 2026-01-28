package com.kh.finalproject.service;

import com.kh.finalproject.dto.MainPage;

public interface MainPageService {

    // 개별 통계 조회
    int getTodayPostCount();
    int getDeliveryPostCount();
    int getCompletePostCount();
    double getAveragePostingTime();
    
    // 전체 대시보드 데이터 조회 (선택사항)
    MainPage getDashboardData();
}