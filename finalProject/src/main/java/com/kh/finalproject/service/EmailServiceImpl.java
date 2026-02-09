package com.kh.finalproject.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.kh.finalproject.mapper.EmailMapper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final EmailMapper mapper;
	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

	/**
	 * 1. 컨트롤러가 호출하는 메인 메서드
	 * (기존에 비어있던 것을 수정함)
	 */
	@Override
	public String sendEmail(String email) {
		// "signup"은 templates/email/signup.html 템플릿을 쓰겠다는 뜻
		return sendEmail("signup", email);
	}

	/**
	 * 2. 실제 메일 발송 로직 (타입별 처리)
	 */
	@Override
	public String sendEmail(String type, String email) {

		// 1) 인증키 생성
		String authKey = createAuthKey();
		System.out.println("🔑 생성된 인증키: " + authKey);

		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);

		// 2) DB 저장 시도
		try {
			if (!storeAuthKey(map)) {
				System.out.println("❌ DB에 인증키 저장 실패 (이메일 없음 등)");
				return null;
			}
		} catch (Exception e) {
			System.out.println("❌ DB 저장 중 에러 발생!");
			e.printStackTrace();
			return null;
		}

		// 3) 메일 발송 준비
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setTo(email);
			helper.setSubject("[boardProject] 회원 가입 인증번호입니다.");
			
			// 템플릿 로딩 시도
			String htmlContent = loadHtml(authKey, type);
			helper.setText(htmlContent, true);

			// 로고 이미지 첨부 (이미지가 없으면 에러 날 수 있음 -> 예외처리)
			/*
			try {
				helper.addInline("logo", new ClassPathResource("static/images/logo.jpg"));
			} catch (Exception e) {
				System.out.println("⚠️ 로고 이미지 첨부 실패 (파일 없음 - 발송은 계속 진행)");
			}
			 */
			// 4) ★★★ 실제 메일 전송 ★★★
			System.out.println("🚀 메일 서버로 전송 시도: " + email);
			mailSender.send(mimeMessage);
			System.out.println("✅ 메일 전송 성공!");

			return authKey;

		} catch (Exception e) {
			// ★ 요청하신 에러 출력 코드 ★
			System.out.println("❌❌❌ 메일 발송 중 치명적 오류 발생! ❌❌❌");
			System.out.println("에러 내용: " + e.getMessage());
			e.printStackTrace(); // 콘솔에 에러 상세 내용 출력
			return null;
		}
	}

	// HTML 템플릿 로딩
	private String loadHtml(String authKey, String type) {
		Context context = new Context();
		context.setVariable("authKey", authKey);
		return templateEngine.process("email/" + type, context);
	}

	// 인증키 DB 저장 (특수문자 제거 및 정리 완료)
	@Transactional(rollbackFor = Exception.class)
	public boolean storeAuthKey(Map<String, String> map) {
		int result = mapper.updateAuthKey(map.get("email"), map.get("authKey"));
		if (result == 0) {
			result = mapper.insertAuthKey(map);
		}
		return result > 0;
	}

	// 인증키 생성
	private String createAuthKey() {
		return UUID.randomUUID().toString().substring(0, 6);
	}

	// 인증키 확인
	@Override
	public int checkAuthKey(Map<String, String> map) {
		return mapper.checkAuthKey(map);
	}

	// 사용하지 않는 메서드 (인터페이스 규격 맞춤용)
	@Override
	public String sendEmail(String email, String subject, String body) {
		return null;
	}
}