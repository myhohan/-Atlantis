package com.kh.finalproject.service;

import java.util.Map;

/* github 참고하여 만듬 */

public interface EmailService {

	/** 이메일 보내기 서비스
	 * @param type : 무슨 이메일을 발송할지 구분할 key로 쓰임
	 * @param email
	 * @return
	 */
	String sendEmail(String type, String email);
	
	/** 입력받은 이메일, 인증번호가 DB에 있는지 조회 서비스
	 * @param map (email, authKey)
	 * @return
	 */
	int checkAuthKey(Map<String, String> map);
}
