package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPage {
    
    private int todayPost;          // 오늘 접수 건수
    private int deliveryPost;       // 배송 중 건수
    private int completePost;       // 배송 완료 건수
    private double averagePostingTime; // 평균 배송 시간 (일 단위)
    
}