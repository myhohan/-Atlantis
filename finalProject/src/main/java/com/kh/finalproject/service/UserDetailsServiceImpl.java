package com.kh.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.finalproject.dto.Member;
import com.kh.finalproject.mapper.MemberMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        
        // 1. DB에서 회원 조회 (기존에 만들어둔 login 메서드 재활용 가능)
        // 주의: MemberMapper의 login 메서드는 비밀번호 비교 없이 '이메일로 회원 정보만 가져오도록' 쿼리가 되어 있어야 함.
        Member loginMember = null;
        try {
            loginMember = mapper.login(memberEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginMember == null) {
            throw new UsernameNotFoundException("일치하는 회원이 없습니다.");
        }

        // 2. UserDetails 객체로 변환하여 반환 (Security가 비밀번호 비교를 알아서 함)
        return User.builder()
                .username(loginMember.getMemberEmail())
                .password(loginMember.getMemberPw()) // DB에 저장된 암호화된 비밀번호
                .roles("USER") // 권한 설정 (필요 시 DB에서 가져와야 함)
                .build();
    }
}