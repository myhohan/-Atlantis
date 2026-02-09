package com.kh.finalproject.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.CompanyManagement;
import com.kh.finalproject.service.CompanyManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("company") 
@RequiredArgsConstructor
public class CompanyManagementController {

    private final CompanyManagementService service;

    /**
     * 1. 전체 배송 건수 조회
     * URL: GET /company/totalDelivery
     */
    @GetMapping("totalDelivery")
    public int totalDelivery() {
        int count = service.getTotalDelivery();
        log.info("총 배송 건수 조회: {}", count);
        return count;
    }

    /**
     * 2. 활성 기사 수 조회
     * URL: GET /company/activePersonnel?status=배송중
     */
    @GetMapping("activePersonnel")
    public int activePersonnel(@RequestParam(value = "status", defaultValue = "배송중") String status) {
        
        return service.getActivePersonnel(status);
    }

    /**
     * 3. 고객 만족도 조회
     * URL: GET /company/customerSatisfaction
     */
    @GetMapping("customerSatisfaction")
    public int customerSatisfaction() {
        return service.getCustomerSatisfaction();
    }

    /**
     * 4. 기사 상세 정보 조회 (이름으로 검색)
     * URL: POST /company/personnelInfo
     * Body: { "personnelName": "홍길동" }
     */
    @PostMapping("personnelInfo")
    public Map<String, Object> personnelManagement(@RequestBody Map<String, String> params) {
        String name = params.get("personnelName");
        log.info("기사 정보 조회 요청: {}", name);
        return service.getPersonnelManagement(name);
    }

    /**
     * 5. 최근 배송 활동 조회 (운송장 번호 기준)
     * URL: GET /company/recentActivity?postNo=12345
     */
    @GetMapping("recentActivity")
    public String recentDeliveryActivity(@RequestParam("postNo") String postNo) {
        return service.getRecentDeliveryActivity(postNo);
    }

    /**
     * 6. 현재 배송 상태 조회 (운송장 번호 기준)
     * URL: GET /company/currentStatus?postNo=12345
     */
    @GetMapping("currentStatus")
    public String currentStatus(@RequestParam("postNo") String postNo) {
        return service.getCurrentStatus(postNo);
    }

    /**
     * 7. 리포트 분석 데이터 조회 (종합 정보)
     * URL: GET /company/reportAnalysis?postNo=12345
     */
    @GetMapping("reportAnalysis")
    public CompanyManagement reportAnalysis(@RequestParam("postNo") String postNo) {
        log.info("리포트 분석 요청: postNo={}", postNo);
        return service.getReportAnalysis(postNo);
    }
}