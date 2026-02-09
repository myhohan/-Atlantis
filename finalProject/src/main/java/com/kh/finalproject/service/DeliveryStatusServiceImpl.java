package com.kh.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.dto.Pagination;
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
    
   
    
 
    public Map<String, Object> selectDeliveryList(Map<String, Object> paramMap, int cp) {
        
        // 1. 전체 게시글 수 조회
        int listCount = mapper.getListCount(paramMap);
        
        // 2. Pagination 객체 생성 (전체 글 수, 현재 페이지, 보여질 글 개수, 보여질 페이지 개수)
       
            Pagination pagination = new Pagination(listCount, cp, 10, 10);

            // [수정] RowBounds 대신 계산된 숫자를 맵에 넣기
            int offset = (pagination.getCurrentPage() - 1) * pagination.getLimit();
            int limit = pagination.getLimit();

            paramMap.put("offset", offset);
            paramMap.put("limit", limit);

           
            List<DeliveryStatus> deliveryList = mapper.selectDeliveryList(paramMap); 
            
           
        
        // 4. Map에 담아서 리턴
        Map<String, Object> map = new HashMap<>();
        map.put("pagination", pagination);
        map.put("deliveryList", deliveryList);
        
        return map;
    }
}