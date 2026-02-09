package com.kh.finalproject.service;

import com.kh.finalproject.dto.Tracking;

public interface TrackingService {

    /**
     * 배송 아이템 추적 (상세 정보)
     * @param postNo 운송장 번호
     * @return Tracking 객체
     */
    Tracking trackItem(String postNo);

    /**
     * 배송 상태 단순 확인 (String 반환)
     * @param postNo 운송장 번호
     * @return 배송 상태 문자열
     */
    String checkTransportStatus(String postNo);
}
