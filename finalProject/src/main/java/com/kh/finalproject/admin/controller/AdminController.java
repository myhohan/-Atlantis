package com.kh.finalproject.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // 또는 BCryptPasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kh.finalproject.admin.model.service.AdminService;
import com.kh.finalproject.dto.Board;
import com.kh.finalproject.dto.DeliveryStatus;
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.PaymentManagement;
import com.kh.finalproject.service.DeliveryStatusService;
import com.kh.finalproject.service.MemberService;
import com.kh.finalproject.service.PaymentManagementService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminController {
	private final PaymentManagementService paymentService;
    private final DeliveryStatusService deliveryService;
    private final MemberService memberService;
	private final AdminService service;
    
    // AdminServiceImpl에서 비밀번호 검사를 수행하므로, 
    // 여기서는 굳이 안 써도 되지만 구조상 남겨두어도 무방합니다.
    private final PasswordEncoder passwordEncoder; 

    // =====================================================================
    // [★ 핵심 수정] 로그인 메서드
    // =====================================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member inputMember) {
        
        // 1. Service 호출 (여기서 이미 ID/PW 검사 다 끝남!)
        Member loginMember = service.login(inputMember);

        // 2. 실패 체크 (Service가 null을 리턴하면 실패)
        if (loginMember == null) {
            return ResponseEntity.status(401).body("없는 아이디 또는 비밀번호 불일치");
        }

        // ★ 여기에 비밀번호 검사 코드가 없어야 합니다! ★

        // 3. 성공 처리
        Map<String, Object> result = new HashMap<>();
        result.put("memberNo", loginMember.getMemberNo());
        result.put("memberEmail", loginMember.getMemberEmail());
        result.put("memberNickname", loginMember.getMemberNickname());
        result.put("authority", loginMember.getAuthority());

        return ResponseEntity.ok(result);
    }

    // =====================================================================
    // 로그아웃
    // =====================================================================
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		try {
			session.invalidate(); // 세션 무효화
			return ResponseEntity.status(HttpStatus.OK)
					.body("로그아웃이 완료되었습니다");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("로그아웃 중 예외 발생 : " + e.getMessage());
		}
	}
	
    // =====================================================================
    // 관리자 계정 발급
    // =====================================================================
	@PostMapping("/createAdminAccount")
	public ResponseEntity<String> createAdminAccount(@RequestBody Member member) {
		try {
			// 1. 기존 이메일 중복 검사
			int checkEmail = service.checkEmail(member.getMemberEmail());
			
			// 2. 중복이면 409 Conflict 반환
			if(checkEmail > 0) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("이미 사용중인 이메일입니다.");
			}
			
			// 3. 계정 생성 (서비스에서 비밀번호 생성 및 암호화 수행)
			String accountPw = service.createAdminAccount(member);
			
			if (accountPw != null) {
				// 201 Created : 자원 생성 성공
				return ResponseEntity.status(HttpStatus.CREATED).body(accountPw);				
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("계정 생성 실패");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("관리자 계정 생성 중 문제 발생: " + e.getMessage());
		}
	}
	
    // =====================================================================
    // 관리자 계정 목록 조회
    // =====================================================================
	@GetMapping("/adminAccountList")
	public ResponseEntity<Object> adminAccountList() {
		try {
			List<Member> adminList = service.adminAccountList();
			return ResponseEntity.status(HttpStatus.OK).body(adminList);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("목록 조회 중 에러: " + e.getMessage());
		}
	}
	
    // =====================================================================
    // 통계: 최대 조회수 게시글
    // =====================================================================
	@GetMapping("/maxReadCount")
	public ResponseEntity<Object> maxReadCount() {
		try {
			Board board = service.maxReadCount();
			return ResponseEntity.status(HttpStatus.OK).body(board);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
	
    // =====================================================================
    // 탈퇴 회원 리스트 조회
    // =====================================================================
	@GetMapping("/withdrawnMemberList")
	public ResponseEntity<Object> selectWithdrawnMemberList() {
		try {
			List<Member> withdrawnMemberList = service.selectWithdrawnMemberList();
			return ResponseEntity.status(HttpStatus.OK).body(withdrawnMemberList);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("탈퇴한 회원 목록 조회 중 문제 발생 : " + e.getMessage());
		}
	}
	
    // =====================================================================
    // 탈퇴 회원 복구
    // =====================================================================
	@PutMapping("/restoreMember")
	public ResponseEntity<String> restoreMember(@RequestBody Member member) {
		try {
			int result = service.restoreMember(member.getMemberNo());
			
			if(result > 0) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(member.getMemberNo() + "번 회원 복구 완료");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("유효하지 않은 memberNo : " + member.getMemberNo());
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("탈퇴 회원 복구 중 문제 발생 : " + e.getMessage());
		}
	}
	/*
	 * 결제 stats 및 배송 count 추가
	 */
	@ResponseBody
    @GetMapping("/payments/stats")
    public List<PaymentManagement> getStats() {
		System.out.println("통계 데이터 요청 들어옴!");
		return paymentService.selectPaymentStats();
    
	}

    // 배송 현황 데이터 API (DeliveryStatusService 활용)
    @ResponseBody
    @GetMapping("/delivery/counts")
    public List<DeliveryStatus> getDeliveryCounts() {
    	System.out.println("통계 데이터 요청 들어옴!");
    	return deliveryService.getDeliveryStatusCounts();
    }
    
    @ResponseBody
    @GetMapping("/payments/todayStats")
    public List<PaymentManagement> gettodayStats(){
    	System.out.println("오늘 데이터 요청 들어옴!");
    	return paymentService.todayPaymentStats();
    }
}