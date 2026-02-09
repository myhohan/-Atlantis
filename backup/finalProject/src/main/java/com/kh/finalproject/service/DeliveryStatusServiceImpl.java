package com.kh.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.mapper.DeliveryStatusMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusMapper mapper;

    @Override
    public DeliveryStatus searchItem(String postNo) {
        log.info("운송장 검색 요청: {}", postNo);
        return mapper.selectDeliveryByPostNo(postNo);
    }

    @Override
    public int getTotalCount() {
        return mapper.selectTotalCount();
    }

    @Override
    public int getCountByStatus(String status) {
        // status 값이 없으면 0 리턴
        if(status == null || status.isEmpty()) return 0;
        return mapper.selectCountByStatus(status);
    }

    @Override
    public List<DeliveryStatus> getDeliveryList(String filterStatus) {
        // 필터가 '전체'이거나 없으면 전체 목록 조회
        if (filterStatus == null || filterStatus.equals("전체") || filterStatus.isEmpty()) {
            return mapper.selectAllList();
        } 
        // 그 외에는 상태별 조회
        else {
            return mapper.selectListByStatus(filterStatus);
        }
    }
}