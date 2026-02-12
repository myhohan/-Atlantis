package com.kh.finalproject.controller;

import java.util.List;
import java.util.Map;

// [중요] 세션 관리를 위한 필수 임포트
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.finalproject.dto.DeliveryRequest;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Mypage;
import com.kh.finalproject.service.MyPageProfileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage") // [수정] 소문자로 통일 (404 에러 방지)
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageProfileService service;

    /**
     * 1. 마이페이지 메인 (정보 조회)
     */
    @GetMapping("/info")
    public String info(HttpSession session, Model model) {
    	System.out.println("🔎 [마이페이지 접속] 현재 세션 ID: " + session.getId());
        // 1. 세션에서 로그인 정보 꺼내기
        Member loginMember = (Member) session.getAttribute("loginMember");
        
        // 2. 로그인 체크 (없으면 메인으로)
        if (loginMember == null) {
            System.out.println(">>> [마이페이지] 로그인 정보 없음 -> 메인으로 이동");
            return "redirect:/";
        }

        System.out.println(">>> [마이페이지] 접속 성공: " + loginMember.getMemberNickname());

        // 3. DB에서 최신 정보 조회
        int memberNo = loginMember.getMemberNo();
        Mypage profile = service.selectProfile(memberNo);
        List<DeliveryRequest> parcelList = service.selectMyParcelList(memberNo);
        List<DeliveryRequest> paymentList = service.selectMyPaymentList(memberNo);

        // 4. 모델에 담아서 화면으로 전달
        model.addAttribute("profile", profile);
        model.addAttribute("parcelList", parcelList);
        model.addAttribute("paymentList", paymentList);

        return "mypage";
    }

    /**
     * 2. 프로필 이미지 변경
     */
    @PostMapping("/profile")
    public String profile(
            @RequestParam("profileImg") MultipartFile profileImg,
            HttpSession session,
            RedirectAttributes ra) throws Exception {
    	
        Member loginMember = (Member) session.getAttribute("loginMember");
    	if (loginMember == null) return "redirect:/";
        
        int result = service.profile(profileImg, loginMember);

        if (result > 0) ra.addFlashAttribute("message", "프로필 이미지가 변경되었습니다.");
        else            ra.addFlashAttribute("message", "변경 실패");

        return "redirect:/mypage/info";
    }

    /**
     * 3. 회원 정보 수정 (닉네임, 전화번호, 주소)
     */
    @PostMapping("/info")
    public String updateInfo(
            Member inputMember,
            @RequestParam("updateAddress") String[] memberAddress,
            HttpSession session,
            RedirectAttributes ra) {

        Member loginMember = (Member) session.getAttribute("loginMember");

        // 1. 로그인 체크
        if (loginMember == null) return "redirect:/";

        System.out.println(">>> [정보수정] 요청 들어옴: " + inputMember);

        // 2. 보안: 로그인한 사람의 번호로 강제 세팅 (남의 정보 수정 방지)
        inputMember.setMemberNo(loginMember.getMemberNo());
        
        // 3. 서비스 호출
        int result = service.updateInfo(inputMember, memberAddress);

        if (result > 0) {
            ra.addFlashAttribute("message", "정보가 수정되었습니다.");
            
            // ★ [중요] DB가 바뀌었으니, 세션에 있는 내 정보도 갱신해야 화면이 바뀝니다.
            loginMember.setMemberNickname(inputMember.getMemberNickname());
            loginMember.setMemberTel(inputMember.getMemberTel());
            loginMember.setMemberAddress(inputMember.getMemberAddress());
            
            // 갱신된 객체를 다시 세션에 저장
            session.setAttribute("loginMember", loginMember);
            
        } else {
            ra.addFlashAttribute("message", "수정 실패");
        }

        return "redirect:/mypage/info";
    }
    
    /**
     * 4. 비밀번호 변경
     */
    @PostMapping("/changePw")
    public String changePw(
            @RequestParam Map<String, Object> paramMap,
            HttpSession session,
            RedirectAttributes ra) {

        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) return "redirect:/";

        int memberNo = loginMember.getMemberNo();
        
        // 서비스 호출
        int result = service.changePw(paramMap, memberNo);

        if (result > 0) ra.addFlashAttribute("message", "비밀번호가 변경되었습니다.");
        else            ra.addFlashAttribute("message", "현재 비밀번호가 일치하지 않습니다.");

        return "redirect:/mypage/info";
    }

    /**
     * 5. 회원 탈퇴
     */
    @PostMapping("/secession")
    public String secession(
            @RequestParam("memberPw") String memberPw,
            HttpSession session,
            RedirectAttributes ra) {

        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) return "redirect:/";

        int memberNo = loginMember.getMemberNo();
        
        // 서비스 호출
        int result = service.secession(memberPw, memberNo);

        if (result > 0) {
            ra.addFlashAttribute("message", "탈퇴 되었습니다.");
            session.invalidate(); // [중요] 세션 전체 삭제 (로그아웃 처리)
            return "redirect:/";  // 메인으로 이동
        } else {
            ra.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "redirect:/mypage/info"; 
        }
    }
    
    @PostMapping("/update") // 또는 @PutMapping
    public String updateInfo(
        Member inputMember, 
        @SessionAttribute(value = "loginMember", required = false) Member loginMember,
     // "없으면 null을 줄 테니, 메서드 안에서 알아서 처리해라" (에러 안 남) // 현재 세션 정보
        Model model,
        RedirectAttributes ra
        ) {

        // 1. 회원 정보 수정 (DB 반영)
        int result = service.updateInfo(inputMember);

        if (result > 0) {
            // ★ [핵심] DB가 수정되었으면, 세션에 있는 정보도 갈아끼워야 합니다!
            loginMember.setMemberAddress(inputMember.getMemberAddress());
            loginMember.setMemberAddressDetail(inputMember.getMemberAddressDetail());
            loginMember.setMemberPost(inputMember.getMemberPost());
            
            // 닉네임이나 전화번호도 수정했다면 같이 갱신
            loginMember.setMemberNickname(inputMember.getMemberNickname());
            loginMember.setMemberTel(inputMember.getMemberTel());
            
            ra.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
        } else {
            ra.addFlashAttribute("message", "회원 정보 수정 실패");
        }

        return "redirect:/myPage"; // 마이페이지로 재요청
    }
}