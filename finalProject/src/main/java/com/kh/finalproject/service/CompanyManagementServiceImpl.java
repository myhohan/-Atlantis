package com.kh.finalproject.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kh.finalproject.dto.CompanyManagement;
import com.kh.finalproject.mapper.CompanyManagementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor // Mapper 자동 주입
@Transactional(rollbackFor = Exception.class)
public class CompanyManagementServiceImpl implements CompanyManagementService {

    private final CompanyManagementMapper mapper;

    @Override
    public int getTotalDelivery() {
        return mapper.selectTotalDeliveryCount();
    }

    @Override
    public int getActivePersonnel(String status) {
        if (status == null || status.isEmpty()) {
            status = "배송중"; // 기본값 설정
        }
        return mapper.selectActivePersonnelCount(status);
    }

    @Override
    public int getCustomerSatisfaction() {
        return mapper.selectCustomerSatisfaction();
    }

    @Override
    public String getRecentDeliveryActivity(String postNo) {
        if (postNo == null) return "운송장 번호 없음";
        return mapper.selectRecentDeliveryActivity(postNo);
    }

    @Override
    public Map<String, Object> getPersonnelManagement(String personnelName) {
        return mapper.selectPersonnelManagement(personnelName);
    }

    @Override
    public String getCurrentStatus(String postNo) {
        return mapper.selectCurrentStatus(postNo);
    }

    @Override
    public CompanyManagement getReportAnalysis(String postNo) {
        return mapper.selectReportAnalysis(postNo);
    }
}