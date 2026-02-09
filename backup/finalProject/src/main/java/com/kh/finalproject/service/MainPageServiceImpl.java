package com.kh.finalproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.MainPage;
import com.kh.finalproject.mapper.MainPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 조회 전용 최적화
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

    private final MainPageMapper mapper;

    @Override
    public int getTodayPostCount() {
        return mapper.selectTodayPostCount();
    }

    @Override
    public int getDeliveryPostCount() {
        return mapper.selectDeliveryPostCount();
    }

    @Override
    public int getCompletePostCount() {
        return mapper.selectCompletePostCount();
    }

    @Override
    public double getAveragePostingTime() {
        Double avg = mapper.selectAveragePostingTime();
        // 데이터가 없어서 null일 경우 0.0 반환
        return (avg != null) ? avg : 0.0;
    }

    @Override
    public MainPage getDashboardData() {
        return MainPage.builder()
                .todayPost(getTodayPostCount())
                .deliveryPost(getDeliveryPostCount())
                .completePost(getCompletePostCount())
                .averagePostingTime(getAveragePostingTime())
                .build();
    }
}