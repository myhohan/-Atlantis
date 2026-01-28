package com.kh.finalproject.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.Tracking;
import com.kh.finalproject.service.TrackingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("trackInvestigation")
@RequiredArgsConstructor
public class TrackInvestigationController {

    private final TrackingService service;

    /**
     * 배송 추적 상세 정보 조회
     * 
     */
    @PostMapping("trackItem")
    public Tracking trackItem(@RequestBody Map<String, String> params) {
        String postNo = params.get("postNo");
        
        Tracking info = service.trackItem(postNo);
        
        /* * 프론트엔드에서는 info가 null이면 "정보 없음" 처리,
         * 값이 있으면 배송 정보를 출력하면 됨.
         */
        return info;
    }

    /**
     * 배송 상태만 간단 조회
     * 
     */
    @PostMapping("checktransportstatus")
    public String checkTransportStatus(@RequestBody Map<String, String> params) {
        String postNo = params.get("postNo");
        return service.checkTransportStatus(postNo);
    }
}