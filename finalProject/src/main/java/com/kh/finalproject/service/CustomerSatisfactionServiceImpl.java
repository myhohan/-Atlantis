package com.kh.finalproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerSatisfactionServiceImpl implements CustomerSatisfactionService {
	
	/* 고객만족도 평가는 관리자 페이지와 연관 */
	
}
