package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tracking {
    private String postNo;           // 운송장 번호
    private String senderName;       // 보내는 분
    private String recipientName;    // 받는 분
    private String currentStatus;    // 현재 상태 (배송중, 집하완료 등)
    private String currentLocation;  // 현재 위치
    private String updateTime;       // 최근 업데이트 시간
}