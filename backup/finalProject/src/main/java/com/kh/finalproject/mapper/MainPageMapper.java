package com.kh.finalproject.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainPageMapper {

    // 오늘 접수된 건수 조회
    int selectTodayPostCount();

    // 배송 중인 건수 조회
    int selectDeliveryPostCount();

    // 배송 완료된 건수 조회
    int selectCompletePostCount();

    // 평균 배송 시간 조회 (NULL 가능성을 위해 Double 사용)
    Double selectAveragePostingTime();
}
