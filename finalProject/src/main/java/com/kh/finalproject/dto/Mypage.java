package com.kh.finalproject.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Data
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 매개변수생성자
@Builder
public class Mypage {
	
	
	
	
	
	private int fileNo;
	private String filePath;
	private String fileOriginName;
	private String fileUploadDate;
	private int memberNo;
	
	private String memberNickname;
	
	
	
	private String memberAddressDetail;
	private String memberPost;
    private String memberEmail;     // 이메일 (HTML ID: memberID 대응)
      // 이름/닉네임 (HTML ID: memberName 대응)
    private String memberTel;       // 전화번호 (HTML ID: memberPhone 대응)
    private String memberAddress;   // 주소
    private String enrollDate;      // 가입일
    private String profileImg;      // 프로필 이미지 경로
}
