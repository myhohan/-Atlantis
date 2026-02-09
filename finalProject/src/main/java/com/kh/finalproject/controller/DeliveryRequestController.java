package com.kh.finalproject.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.service.CourierBotService;
import com.kh.finalproject.service.DeliveryRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("deliveryrequest")
@RequiredArgsConstructor
public class DeliveryRequestController {
	
	private final CourierBotService botService;
    
	private final DeliveryRequestService service;


    /**
     * 택배 접수 요청
     * 
     */
	@PostMapping("/request")
	public String deliveryRequest(@RequestBody List<DeliveryRequest> requestList) {
	    
	    // 1. 데이터 보정 (결제 성공 상태로 강제 세팅)
	    for(DeliveryRequest req : requestList) {
	        // ★ 여기서 값을 넣어줘야 XML의 #{...}에 들어갑니다.
	        req.setTransportStatus("결제완료"); // 상태 변경
	        req.setIsPayment("Y");            // 결제 여부 Y
	    }

	    // 2. DB 저장 (이제 '결제완료', 'Y'로 저장됨)
	    service.deliveryRequest(requestList); 
	    
	    // 3. 봇 실행 및 결과 리턴 (기존 코드 유지)
	    StringBuilder botMessages = new StringBuilder();
	    for(DeliveryRequest req : requestList) {
	        String realPostNo = botService.reserveDelivery(req);
	        if(botMessages.length() > 0) botMessages.append(", ");
	        botMessages.append(realPostNo);
	    }
	    
	    return "결제 및 접수 완료! [송장번호]: " + botMessages.toString();
	}
	
	// 결제 내역 페이지 이동
	@GetMapping("/payment")
	public String paymentList(Model model) {
	    // 1. DB에서 모든 택배 접수 내역을 가져옴 (혹은 결제된 것만 가져오려면 WHERE IS_PAYMENT='Y' 필요)
	    List<DeliveryRequest> list = service.selectPaymentList(); 
	    
	    // 2. HTML로 데이터 전달
	    model.addAttribute("paymentList", list);
	    
	    return "payment"; // payment.html로 이동
	}
    
}

		
		
		
		


