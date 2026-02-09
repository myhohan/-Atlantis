package com.kh.finalproject.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.service.PaymentManagementService;

@Controller
@RequiredArgsConstructor
public class PaymentManagementController {

    private final PaymentManagementService paymentService; // 서비스 주입

    @GetMapping("/payment")
    public String paymentList(Model model) {
        
        // 1. 서비스 호출 (DTO 리스트를 가져옴)
        List<DeliveryRequest> list = paymentService.selectPaymentList();
        
        // 2. 모델에 담기
        model.addAttribute("paymentList", list);
        
        // 3. payment.html로 이동
        return "payment";
    }
}