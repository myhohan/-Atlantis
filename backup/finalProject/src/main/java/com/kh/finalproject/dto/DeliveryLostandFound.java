package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryLostandFound {
    private int reportNo;             // 신고 번호 (PK)
    private String postNo;            // 운송장 번호
    private String lossType;          // 분실 유형
    private String itemCategory;      // 물품 종류
    private String requestExplanation;// 신고 내용
    private String reportDate;        // 신고 일자
    private String status;            // 현재 상태 (조사중, 발견, 완료 등)
    private String discoverStatus;    // 발견 상태
    
    // 필요 시 필드 추가
}