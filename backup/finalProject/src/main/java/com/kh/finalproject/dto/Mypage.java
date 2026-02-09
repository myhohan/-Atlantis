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
	
}
