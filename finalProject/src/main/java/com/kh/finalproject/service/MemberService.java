package com.kh.finalproject.service;

import java.util.List;

import com.kh.finalproject.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember) throws Exception;
	
	/** 이메일 중복검사 서비스
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);
	
	/** 닉네임 중복검사 서비스
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);
	/** 회원가입 서비스
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signup(Member inputMember, String[] memberAddress);
	
	/** 회원 목록 조회(비동기)
	 * @return memberList
	 */
	List<Member> selectMemberList();
	
	/** 비밀번호 초기화(비동기)
	 * @param inputNo
	 * @return
	 */
	int resetPw(int inputNo);
	
	/** 탈퇴 회원 복구(비동기)
	 * @param inputNo
	 * @return
	 */
	int restoreMember(int inputNo);

	/** 회원번호 조회 
	 * 로그인 기능
	 * **/
	Member selectMemberByNo(int memberNo);

	
	
	
}
