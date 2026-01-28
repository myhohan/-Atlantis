package com.kh.finalproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("deliverystatus")
@RequiredArgsConstructor
public class DeliveryStatusController {

    private final DeliveryStatusService service;

    /**
     * 운송장 번호 검색
     * URL: /deliverystatus/search?postNo=12345
     */
    @GetMapping("search")
    public DeliveryStatus searchItem(@RequestParam("postNo") String postNo) {
        return service.searchItem(postNo);
    }

    /**
     * 전체 요청 건수 조회
     * URL: /deliverystatus/totalCount
     */
    @GetMapping("totalCount")
    public int totalRaisedRequest() {
        return service.getTotalCount();
    }
    
    /**
     * 상태별 건수 조회 (대기, 배송중, 완료 등)
     * URL: /deliverystatus/count?status=배송중
     */
    @GetMapping("count")
    public int countByStatus(@RequestParam("status") String status) {
        return service.getCountByStatus(status);
    }

    /**
     * 배송 목록 조회 (필터링)
     * 
     */
    @PostMapping("list")
    public List<DeliveryStatus> getDeliveryList(@RequestBody Map<String, String> params) {
        String filter = params.get("filter");
        log.info("목록 조회 요청 필터: {}", filter);
        return service.getDeliveryList(filter);
    }
}