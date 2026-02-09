package com.kh.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.DispatcherType; 

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 보안 해제 (개발 편의)
            .csrf(csrf -> csrf.disable()) 
            
            // 2. 주소 권한 설정
            .authorizeHttpRequests(auth -> auth
                // (1) 정적 리소스 등은 무조건 허용
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**", "/assets/**", "/favicon.ico", 
                    "/error", "/upload/**"
                ).permitAll()
                
                // (2) forward 방식 이동 허용
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                
                // ★★★ [핵심] 모든 경로(/**)를 다 허용합니다! ★★★
                // 이유: 우리는 MemberController와 MyPageController에서 
                //      if (session.getAttribute("loginMember") == null) 로 
                //      직접 검사하고 있기 때문에, Security는 검사하지 말고 비켜줘야 합니다.
                .requestMatchers("/**").permitAll() 
            )
            
            // 3. Security의 기본 로그인/로그아웃 기능 끄기
            // (우리가 만든 MemberController의 로그인/로그아웃을 쓰기 위함)
            .formLogin(login -> login.disable())
            .logout(logout -> logout.disable());

        return http.build();
    }
}