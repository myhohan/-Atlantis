package com.kh.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.Member;

@Mapper
public interface AdminMapper {
	
	Member login(String memberEmail);
	
	int checkEmail(String memberEmail);
	
	int createAdminAccount(Member member);
	
	List<Member> adminAccountList();
	
	Board maxReadCount();
	
	List<Member> selectWithdrawnMemberList();
	
	int restoreMember(int memberNo);
}