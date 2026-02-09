package com.kh.finalproject.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kh.finalproject.dto.Member;
import com.kh.finalproject.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginKeepInterceptor implements HandlerInterceptor{
	
	@Autowired
	private MemberService service;
	/* 로그인 유지 쿠키 */
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if(request.getSession().getAttribute("loginMember") != null) {
			
			return true;
		}
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) {
			
			return true;
		}
		
		for(Cookie cookie : cookies) {
			
			if("loginKeep".equals(cookie.getName())) {
				
				try {
					
					int memberNo = Integer.parseInt(cookie.getValue());
					
					Member loginMember = service.selectMemberByNo(memberNo);
					
					if(loginMember != null) {
						request.getSession().setAttribute("loginMember", loginMember);
					}
					
				}catch (Exception e){
					e.printStackTrace();
				}
				
				break;
				
			}
			
		}		
		
		return true;
	}
	
	
}

