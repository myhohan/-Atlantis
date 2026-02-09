package com.kh.finalproject.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.Member;

@Mapper
public interface AdminMapper {

	com.kh.finalproject.dto.Member login(String memberEmail);

	int checkEmail(String memberEmail);

	int createAdminAccount(com.kh.finalproject.dto.Member member);

	List<com.kh.finalproject.dto.Member> adminAccountList();

	com.kh.finalproject.dto.Board maxReadCount();

	List<com.kh.finalproject.dto.Member> selectWithdrawnMemberList();

	int restoreMember(int memberNo);

}
