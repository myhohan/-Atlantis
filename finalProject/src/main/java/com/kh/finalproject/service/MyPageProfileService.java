package com.kh.finalproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Mypage;
import com.kh.finalproject.dto.UploadFile;


/*마이페이지 github 참고하여 작성 완료
 * 
 */
public interface MyPageProfileService {

/** 회원 정보 수정 서비스
 * @param inputMember
 * @param memberAddress
 * @return
 */
	int updateInfo(Member inputMember, String[] memberAddress);
	
/** 비밀번호 변경 서비스
 * @param paramMap
 * @param memberNo
 * @return
 */
	int changePw(Map<String, Object> paramMap, int memberNo);
	
/** 회원 탈퇴 서비스
 * @param memberPw
 * @param memberNo
 * @return
 */
	int secession(String memberPw, int memberNo);
	
	/** 파일 업로드 테스트 1
	 * @param uploadFile
	 * @return
	 */
	String fileUpload1(MultipartFile uploadFile) throws Exception;
	
	/** 파일 업로드 테스트 2
	 * @param uploadFile
	 * @param memberNo
	 * @return
	 */
	int fileUpload2(MultipartFile uploadFile, int memberNo) throws Exception;
	
	/** 파일 목록 조회 서비스
	 * @param memberNo
	 * @return
	 */
	List<UploadFile> fileList(int memberNo);
	
	/** 여러 파일 업로드 서비스
	 * @param aaaList
	 * @param bbbList
	 * @param memberNo
	 * @return
	 */
	int fileUpload3(List<MultipartFile> aaaList,
			List<MultipartFile> bbbList,
			int memberNo) throws Exception;
	
	/** 프로필 이미지 변경 서비스
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	int profile(MultipartFile profileImg, Member loginMember) throws Exception;

	Mypage selectProfile(int memberNo);

	List<DeliveryRequest> selectMyParcelList(int memberNo);

	List<DeliveryRequest> selectMyPaymentList(int memberNo);

	int updateInfo(Member inputMember);
}
