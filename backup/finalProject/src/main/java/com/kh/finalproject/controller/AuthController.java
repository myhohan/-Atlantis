package com.kh.finalproject.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@PostMapping("/api/login")
	public ResponseEntity<?> login() {
		// 1. 로그인 로직 (ID/PW 검증) 성공 후 토큰 생성
		String accessToken = null;// = "a" // 생성된 JWT 토큰이라 가정
				
		// 2. 쿠키 생성 (ResponseCookie 사용)
		ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
			.httpOnly(true) // 중요: 자바스크립트로 접근 불가 (XSS 방지)
			.secure(true) // 운영 환경(HTTPS)에서는 true로 설정
			.path("/") // 모든 경로에서 쿠키 유효
			.maxAge(60 * 60) // 쿠키 수명 ( 초 단위 여기선 1시간)
			.sameSite("None") // 프론트(3000)와 백(8080) 포트가 다르면 None 설정
			.build();
		
		// 3. 헤더에 쿠키를 실어서 보냄
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE,  cookie.toString())
				.body("로그인 성공");
	}
}
