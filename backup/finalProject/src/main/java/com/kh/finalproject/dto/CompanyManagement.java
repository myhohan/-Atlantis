package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyManagement {
    
    private String totalDeliveryNumber;   // 총 배송 건수
    private String activePersonnel;       // 활성 기사 수
    private String monthlyProfit;         // 월 매출
    private String status;                // 상태
    private String summary;               // 개요
    private String recentDeliveryActivity;// 최근 배송 활동
    private String postNo;                // 운송장 번호
    private String deliveryFeats;         // 배송 성과
    private String deliveryFeatsImage;    // 성과 이미지
    private String personnelName;         // 기사 이름
    private int customerEvaluation;       // 고객 평점
}