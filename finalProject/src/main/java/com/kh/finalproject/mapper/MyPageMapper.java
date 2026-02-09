package com.kh.finalproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Mypage;
import com.kh.finalproject.dto.UploadFile;

@Mapper
public interface MyPageMapper {
	/** 회원 정보 수정 SQL
	 * @param inputMember
	 * @return
	 */
	int updateInfo(Member inputMember);
	
	/** 회원의 암호화된 비밀번호 조회
	 * @param memberNo
	 * @return 암호화된 비밀번호
	 */
	String selectPw(int memberNo);
	
	/** 비밀번호 변경
	 * @param paramMap
	 * @return result
	 */
	int changePw(Map<String, Object> paramMap);
	
	/** 회원 탈퇴 SQL (updatE)
	 * @param memberNo
	 * @return
	 */
	int secession(int memberNo);
	
	/** 파일 정보를 DB에 삽입SQL (insert)
	 * @param uf
	 * @return
	 */
	int insertUploadFile(UploadFile uf);
	
	/** 파일 목록 조회하는 SQL
	 * @param memberNo
	 * @return
	 */
	List<UploadFile> fileList(int memberNo);
	
	/** 프로필 이미지 변경 SQL
	 * @param member
	 * @return
	 */
	int profile(Member member);
	
	/** 로그인한 회원 정보 조회 (마이페이지 메인용)
     * @param memberNo
     * @return
     */
    Mypage selectProfile(int memberNo);

	List<DeliveryRequest> selectMyParcelList(int memberNo);

	List<DeliveryRequest> selectMyPaymentList(int memberNo);
	
}
