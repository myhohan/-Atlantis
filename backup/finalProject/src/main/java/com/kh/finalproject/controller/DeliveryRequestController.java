package com.kh.finalproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.service.DeliveryRequestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("deliveryrequest")
@RequiredArgsConstructor
public class DeliveryRequestController {

    private final DeliveryRequestService service;

    /**
     * 택배 접수 요청
     * 
     */
    @PostMapping("/request")
    public String deliveryRequest(@RequestBody List<DeliveryRequest> requestList) {
        
        log.info("택배 접수 요청 들어옴: 총 {} 건", requestList.size());
        
        int result = service.deliveryRequest(requestList);
        
        if (result > 0) {
            return result + "건의 택배 접수가 완료되었습니다.";
        } else {
            return "택배 접수에 실패했습니다.";
        }
    }
}
		
		
		
		


