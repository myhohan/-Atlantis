package com.kh.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.DeliveryLostandFound;
import com.kh.finalproject.service.DeliveryLostandFoundService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/lostandfound")
@RequiredArgsConstructor
public class DeliveryLostandFoundController {

    private final DeliveryLostandFoundService service;

    
    /**
     * 전체 보고서 갯수 조회
     * GET 방식 권장
     */
    @GetMapping("totalReport")
    @ResponseBody
    public int totalReport() {
        int count = service.getTotalReportCount();
        log.info("전체 보고서 수: {}", count);
        return count;
    }

    /**
     * 전체 조사중 갯수 조회
     */
    @GetMapping("totalInvestigation")
    @ResponseBody
    public int totalInvestigation() {
        return service.getInvestigationCount();
    }

    /**
     * 전체 발견 갯수 조회
     */
    @GetMapping("totalDiscover")
    @ResponseBody
    public int totalDiscover() {
        return service.getDiscoverCount();
    }

    /**
     * 분실 신고 등록
     * DTO로 데이터를 한 번에 받음
     */
    @PostMapping("fileReport")
    @ResponseBody
    public String fileReport(@RequestBody DeliveryLostandFound dto) {
        return service.fileReport(dto);
    }
    
    @GetMapping("/list")
    public List<DeliveryLostandFound> getList() {
        List<DeliveryLostandFound> list = service.getRecentReports();
        
        // ★ 만약 DB 조회 결과가 null이면, 빈 리스트라도 보내야 에러가 안 납니다!
        if (list == null) {
            return new ArrayList<>(); 
        }
        
        return list;
    }
}

