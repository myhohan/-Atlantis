package com.kh.finalproject.controller;

import java.util.List;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.finalproject.dto.Member;
import com.kh.finalproject.service.MemberService;

import lombok.extern.slf4j.Slf4j;





@Controller
@RequestMapping("member") // 기본 주소: /member
@Slf4j
public class MemberController {

    @Autowired
    private MemberService service;

    
    // [1] 로그인 화면 보여주기 (GET)
    
    @GetMapping("login")
    public String loginPage() {
        return "login"; // templates/login.html 파일을 보여줌
    }

    
    // [2] 로그인 기능 처리하기 (POST)
    
    @PostMapping("login")
    public String login(
            Member inputMember,
            RedirectAttributes ra,
            HttpSession session,
            @RequestParam(value = "saveId", required = false) String saveId,
            HttpServletResponse resp) throws Exception {

        Member loginMember = service.login(inputMember);

        if (loginMember == null) {
            ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
            return "redirect:/member/login"; // 실패 시 다시 로그인 화면으로
        } else {
            session.setAttribute("loginMember", loginMember);
            
            System.out.println(">>> [로그인 성공] 세션 저장: " + loginMember.getMemberEmail());

            Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
            cookie.setPath("/");
            if (saveId != null) cookie.setMaxAge(60 * 60 * 24 * 30);
            else cookie.setMaxAge(0);
            resp.addCookie(cookie);
            
            
            return "redirect:/"; // 성공 시 메인으로
        }
    }

    // [3] 로그아웃
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";
    }

    // [4] 회원가입 페이지 이동
    @GetMapping("signup")
    public String signupPage(Model model) {
        model.addAttribute("member", new Member());
        return "signup";
    }

    // [5] 회원가입 처리
    @PostMapping("signup")
    public String signup(Member inputMember,
                         @RequestParam("memberAddress") String[] memberAddress,
                         RedirectAttributes ra) {

        int result = service.signup(inputMember, memberAddress);

        if (result > 0) {
            ra.addFlashAttribute("message", "가입을 환영합니다!");
            return "redirect:/member/login";
        } else {
            ra.addFlashAttribute("message", "회원 가입 실패...");
            return "redirect:/member/signup";
        }
    }

    // 기타 메서드들 (이메일 체크 등)
    @ResponseBody
    @GetMapping("checkEmail")
    public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
        return service.checkEmail(memberEmail);
    }

    @ResponseBody
    @GetMapping("checkNickname")
    public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
        return service.checkNickname(memberNickname);
    }
    
    @ResponseBody
    @GetMapping("selectMemberList")
    public List<Member> selectMemberList() {
        return service.selectMemberList();
    }

    @ResponseBody
    @PutMapping("resetPw")
    public int resetPw(@RequestBody int inputNo) {
        return service.resetPw(inputNo);
    }

    @ResponseBody
    @PutMapping("restoreMember")
    public int restoreMember(@RequestBody int inputNo) {
        return service.restoreMember(inputNo);
    }
}