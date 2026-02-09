package com.kh.finalproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.Tracking;

@Mapper
public interface TrackingMapper {

    // 운송장 번호로 배송 정보 조회
    Tracking selectTrackingInfo(String postNo);
    
    // 운송장 번호로 현재 상태(String)만 조회
    String selectTransportStatus(String postNo);

}
