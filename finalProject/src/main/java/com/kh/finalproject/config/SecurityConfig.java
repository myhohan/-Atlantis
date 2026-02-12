package com.kh.finalproject.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.DispatcherType; 
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        // 1. CSRF 보안 해제
            .csrf(csrf -> csrf.disable()) 
            
            // 2. 주소 권한 설정
            .authorizeHttpRequests(auth -> auth
                // (1) forward 방식 이동 허용 (에러 방지를 위해 맨 위로)
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()

                // (2) 정적 리소스 및 특정 경로 허용
                .requestMatchers(
                    "/css/**", "/js/**", "/images/**", "/assets/**", "/favicon.ico", 
                    "/error", "/upload/**", "/api/**" // ★ 리액트용 API 경로도 명시적으로 추가
                ).permitAll()
                
                // (3) ★★★ 모든 경로 허용 (이게 가장 넓은 범위이므로 아래쪽에 위치)
                .requestMatchers("/**").permitAll() 

                // (4) [중요] 모든 설정의 끝에 anyRequest가 와야 함
                // 하지만 위에서 "/**"를 permitAll 했으므로 사실상 모든 문이 열린 상태야.
                .anyRequest().permitAll() 
            )
            
            // 3. Security 기본 로그인/로그아웃 끄기
            .formLogin(login -> login.disable())
            .logout(logout -> logout.disable());

        return http.build();
    }
    
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🚨 리액트 포트 5174를 명확히 지정 (localhost와 127.0.0.1은 다를 수 있음)
        config.setAllowedOrigins(List.of("http://localhost:5173")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}