package com.kh.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.kh.finalproject.dto.DeliveryLostandFound;

@Mapper
public interface DeliveryLostandfoundMapper {

    // 전체 신고 건수 조회
    int selectTotalReportCount();
    
    // 조사중인 건수 조회 (상태값을 파라미터로 받음)
    int selectTotalInvestigationCount(String status);
    
    // 발견된 건수 조회 (상태값을 파라미터로 받음)
    int selectTotalDiscoverCount(String status);
    
    // 분실물 신고 등록 (성공 시 1 반환)
    int insertReport(DeliveryLostandFound dto);
    
 // DeliveryLostandfoundMapper.java


        // ★ 목록 조회 메서드 추가 (XML ID와 이름 일치 필수!)
        List<DeliveryLostandFound> selectRecentReports();
        
    }
