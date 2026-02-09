package com.kh.finalproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.PaymentManagement;
import com.kh.finalproject.service.PaymentManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentManagementController {

    private final PaymentManagementService service;
    
    /**
     * 총 결제 금액 조회
     * URL: /payment/total?memberId=user1
     */
    @GetMapping("total")
    public int totalPayment(@RequestParam("memberId") String memberId) {
        return service.getTotalPayment(memberId);
    }
    
    /**
     * 결제 대기 금액 조회
     */
    @GetMapping("pending")
    public int pendingPayment(@RequestParam("memberId") String memberId) {
        return service.getPendingPayment(memberId);
    }
    
    /**
     * 결제 완료 금액 조회
     */
    @GetMapping("complete")
    public int completePayment(@RequestParam("memberId") String memberId) {
        return service.getCompletePayment(memberId);
    }
    
    /**
     * 결제 내역 목록 (필터링 포함)
     * URL: /payment/list (POST body: {"memberId":"user1", "duration":"이번달"})
     */
    @PostMapping("list")
    public List<PaymentManagement> showPaymentList(@RequestBody Map<String, String> params) {
        String memberId = params.get("memberId");
        String duration = params.get("duration");
        
        log.info("결제 내역 조회 요청: ID={}, 기간={}", memberId, duration);
        
        return service.getPaymentList(memberId, duration);
    }
}