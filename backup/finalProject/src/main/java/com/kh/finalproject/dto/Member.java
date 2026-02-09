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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	
	public static final int LEVEL_USER=1;
	public static final int LEVEL_ADMIN=2;
	
	private int memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberNickname;
	private String memberTel;
	private String memberAddress;
	private String profileImg;
	private String enrollDate;
	private String memberDelFl;
	private int authority;
}
