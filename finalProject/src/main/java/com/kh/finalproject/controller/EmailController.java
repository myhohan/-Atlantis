package com.kh.finalproject.controller;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.service.EmailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final 필드에 자동으로 의존성 주입
public class EmailController {

	@Autowired
	private final EmailService service;
	@ResponseBody
	@PostMapping("/signup")
	// 1. 파라미터를 Map<String, String>으로 바꿉니다.
	public String signup(@RequestBody Map<String, String> requestData) {
	    
	    // 2. 껍데기(Map) 안에서 "email" 키에 해당하는 알맹이만 꺼냅니다.
	    String email = requestData.get("email"); 
	    
	    System.out.println("꺼낸 진짜 이메일: " + email); // 로그로 확인해보세요

	    // 3. 알맹이만 서비스로 넘깁니다.
	    service.sendEmail(email); 
	    
	    return "success"; // 리턴값은 본인 코드에 맞게
	}
	
	/** 입력받은 이메일, 인증번호가 DB에 있는지 조회
	 * @param map : email, authKey
	 * @return
	 * 
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, String> map) {
		return service.checkAuthKey(map);
	}
	
	@PostMapping("/send")
    public String sendVerificationMail(@RequestParam("email") String email) {
        
        // 1. 인증번호 생성 (6자리 난수)
        String authCode = createAuthCode();
        
        // 2. 이메일 본문 내용 작성
        String subject = "[내사이트] 회원가입 인증번호입니다.";
        String body = "<h3>요청하신 인증 번호입니다.</h3>" +
                      "<h1>" + authCode + "</h1>" +
                      "<p>감사합니다.</p>";

        // 3. 메일 발송
        service.sendEmail(email, subject, body);
        
        // 4. (중요) 실제로는 여기서 authCode를 DB나 Redis에 저장해야 나중에 검증 가능
        
        return "인증번호 발송 성공";
    }

    // 6자리 난수 생성 메서드
    private String createAuthCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for(int i=0; i<6; i++) {
            key.append(random.nextInt(10));
        }
        return key.toString();
    }
	
	 
};
