package com.kh.finalproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.dto.Pagination;
import com.kh.finalproject.mapper.DeliveryStatusMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusMapper mapper;

    @Override
    public Map<String, Object> selectDeliveryList(Map<String, Object> paramMap, int cp) {

        // 1. 검색 조건에 맞는 전체 게시글 수 조회
        int listCount = mapper.getListCount(paramMap);

        // 2. 페이지네이션 객체 생성
        // 만약 검색으로 인해 데이터가 줄어들었는데 cp가 listCount보다 큰 경우를 대비
        Pagination pagination = new Pagination(cp, listCount);

        // [중요] 검색 결과가 있는데 OFFSET이 범위를 벗어나는 경우 1페이지로 강제 조정
        if (cp > pagination.getMaxPage() && listCount > 0) {
            cp = 1;
            pagination = new Pagination(cp, listCount);
        }

        // 3. 마이바티스 OFFSET / LIMIT 계산
        // offset : 몇 개의 행을 건너뛸 것인가? (cp - 1) * limit
        int limit = pagination.getLimit();
        int offset = (cp - 1) * limit;

        paramMap.put("offset", offset);
        paramMap.put("limit", limit);

        // 4. 목록 조회
        List<DeliveryStatus> deliveryList = mapper.selectDeliveryList(paramMap);

        // 5. 결과 맵 구성
        Map<String, Object> map = new HashMap<>();
        map.put("pagination", pagination);
        map.put("deliveryList", deliveryList);

        return map;
    }

	@Override
	public DeliveryStatus searchItem(String postNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCountByStatus(String status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DeliveryStatus> getDeliveryList(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<DeliveryStatus> getDeliveryStatusCounts() {
		// TODO Auto-generated method stub
		return null;
	}

	
}