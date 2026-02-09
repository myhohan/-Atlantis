package com.kh.finalproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.Tracking;
import com.kh.finalproject.mapper.TrackingMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrackingServiceImpl implements TrackingService {

    private final TrackingMapper mapper;

    @Override
    public Tracking trackItem(String postNo) {
        log.info("배송 추적 요청: {}", postNo);
        
        Tracking result = mapper.selectTrackingInfo(postNo);
        
        if (result == null) {
            log.warn("해당 운송장 번호({})의 정보가 존재하지 않습니다.", postNo);
            return null; 
        }
        
        return result;
    }

    @Override
    public String checkTransportStatus(String postNo) {
        String status = mapper.selectTransportStatus(postNo);
        
        if (status == null) {
            return "정보 없음";
        }
        
        return status;
    }
}
