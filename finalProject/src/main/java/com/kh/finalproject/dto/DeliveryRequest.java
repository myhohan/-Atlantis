package com.kh.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequest {
    
    private String postNo;             // 운송장 번호 (PK, 시퀀스로 자동생성 예상)
    
    // 보내는 사람 정보
    private String senderName;
    private String senderPhone;     
    private String senderEmail;
    private String senderAddress;
    
    // 받는 사람 정보
    private String recipientName;   
    private String recipientPhone;  
    private String recipientAddress; 
    
    // 물품 정보
    private String postType;        // 물품 종류
    private int postWeight;      // 무게
    private String postExplanation; // 물품 설명 (요청사항)
    private String regDate;
    private String isPayment;
    private String transportStatus;
}

