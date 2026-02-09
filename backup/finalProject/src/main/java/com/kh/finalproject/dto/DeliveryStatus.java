package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatus {
    private String postNo;          // 운송장 번호
    private String senderName;      // 보내는 사람
    private String recipientName;   // 받는 사람
    private String transportStatus; // 배송 상태 (접수대기, 배송중, 배송완료)
    private String currentLocation; // 현재 위치
    private String updateDate;      // 최근 업데이트 시간
}
