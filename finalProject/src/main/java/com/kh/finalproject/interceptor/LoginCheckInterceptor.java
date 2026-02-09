package com.kh.finalproject.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j 
public class LoginCheckInterceptor implements HandlerInterceptor{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getSession().getAttribute("loginMember") != null) {
			
			return true;
		}
		
		response.sendRedirect("/loginError");
		return false;
	}	

}


