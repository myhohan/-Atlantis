package com.kh.finalproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.DeliveryLostandFound;
import com.kh.finalproject.mapper.DeliveryLostandfoundMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor // 생성자 주입
@Transactional(rollbackFor = Exception.class)
public class DeliveryLostandFoundServiceImpl implements DeliveryLostandFoundService {

    private final DeliveryLostandfoundMapper mapper;

    @Override
    public int getTotalReportCount() {
        return mapper.selectTotalReportCount();
    }

    @Override
    public int getInvestigationCount() {
        // "조사중" 상태인 것만 카운트
        return mapper.selectTotalInvestigationCount("조사중");
    }

    @Override
    public int getDiscoverCount() {
        // "발견" 상태인 것만 카운트
        return mapper.selectTotalDiscoverCount("발견");
    }

    @Override
    public String fileReport(DeliveryLostandFound dto) {
        log.info("분실물 신고 접수 시작: {}", dto);
        
        // 기본 상태 설정 (필요시)
        if(dto.getStatus() == null) {
            dto.setStatus("접수완료");
        }

        int result = mapper.insertReport(dto);

        if (result > 0) {
            return "신고가 정상적으로 접수되었습니다.";
        } else {
            return "신고 접수에 실패했습니다.";
        }
    }
}
/*
package com.kh.finalproject.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryLostandfoundMapper {

	int totalReport(int reportNo);
	
	int totalInvestigation(int reportUnderInvestigation);
	
	int totalDiscover(int discoverStatus);
	
	String fileReport(int fileReport);
	
	자료형 및 매개변수 dto 확인*/

	
