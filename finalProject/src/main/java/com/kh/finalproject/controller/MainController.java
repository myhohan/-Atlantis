package com.kh.finalproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.dto.MainPage;
import com.kh.finalproject.service.MainPageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {

    private final MainPageService service;

    /**
     * 대시보드 전체 데이터 조회 (권장)
     * 
     */
    @GetMapping("/dashboard")
    public MainPage getDashboard() {
        return service.getDashboardData();
    }

    @GetMapping("/today")
    public int showTodayPost() {
        return service.getTodayPostCount();
    }

    @GetMapping("/delivery")
    public int showDeliveryPost() {
        return service.getDeliveryPostCount();
    }

    @GetMapping("/complete")
    public int showCompletePost() {
        return service.getCompletePostCount();
    }
    
    @GetMapping("/averageTime")
    public double showAveragePostingTime() {
        return service.getAveragePostingTime();
    }
}