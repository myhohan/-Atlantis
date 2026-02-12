package com.kh.finalproject.admin.controller;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

import com.kh.finalproject.admin.model.service.DeliveryStatusAPIService;
import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.service.DeliveryStatusService;
import lombok.RequiredArgsConstructor;

@RestController // 👈 JSON 데이터를 반환하기 위해 필수
@RequestMapping("admin/delivery") // 👈 리액트 axios 주소와 일치
@CrossOrigin(origins = "http://localhost:5173") // 👈 CORS 에러 방지
@RequiredArgsConstructor
public class DeliveryStatusAPIController {

    private final DeliveryStatusAPIService service;

    @GetMapping("stats") // 👈 최종 주소: http://localhost:8080/admin/delivery/stats
    public List<DeliveryStatus> getDeliveryStats() {
        // 서비스에서 MyBatis 매퍼를 호출해 데이터를 가져옵니다.
        return service.getDeliveryStatusAPICounts(); 
    }
}