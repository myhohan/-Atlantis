package com.kh.finalproject.admin.model.service;

import java.util.List;

import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.Member;

public interface AdminService {

	Member login(Member inputMember);

	int checkEmail(String memberEmail);

	String createAdminAccount(Member member);

	List<Member> adminAccountList();

	Board maxReadCount();

	List<Member> selectWithdrawnMemberList();

	int restoreMember(int memberNo);

}
