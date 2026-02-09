package com.kh.finalproject.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // [중요] Model 임포트 확인
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// [중요] Member DTO 위치에 맞게 임포트 (패키지명 확인!)
import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Pagination; 

@Controller
@RequestMapping("/") 
public class MainController {

    // 1. 메인 페이지
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
    
    // 2. 로그인 페이지
    // (MemberController에 똑같은 @GetMapping("/login")이 있다면 둘 중 하나는 지워야 합니다!)
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }
    
    // 3. 회원가입 페이지 [수정됨]
    // HTML에 th:object="${member}"가 있어서, 빈 객체를 꼭 보내줘야 합니다.
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("member", new Member()); // 이 줄이 없으면 500 에러!
        return "signup"; 
    }
    
    // 4. 결제 관리 페이지 [추가됨]
    // 아까 코드에는 이 메서드가 없어서 404 에러가 났을 겁니다.
    /* @GetMapping("/payment")
    public String payment(Model model, @RequestParam(value="cp", defaultValue="1") int cp) {
        
        // 1. 임시 데이터 생성 (DB 대용: 55개의 결제 내역 만들기)
        List<Map<String, Object>> allPayments = new ArrayList<>();
        for(int i = 1; i <= 55; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("trackingNumber", "PKG-2024-" + String.format("%03d", i));
            map.put("amount", (i * 1000) + "원");
            map.put("method", i % 2 == 0 ? "신용카드" : "계좌이체");
            map.put("status", i % 3 == 0 ? "pending" : "paid");
            map.put("date", "2024-01-" + String.format("%02d", (i % 30) + 1));
            allPayments.add(map);
        }

        // 2. 페이지네이션 객체 생성 (현재페이지, 전체개수)
        Pagination pagination = new Pagination(cp, allPayments.size());

        // 3. 현재 페이지에 보여줄 리스트 자르기 (SubList)
        int startIndex = (cp - 1) * pagination.getLimit();
        int endIndex = Math.min(startIndex + pagination.getLimit(), allPayments.size());
        
        List<Map<String, Object>> currentList = allPayments.subList(startIndex, endIndex);

        // 4. 모델에 담기
        model.addAttribute("paymentList", currentList);
        model.addAttribute("pagination", pagination);

        return "payment";
    }
    */
    
    // --- 나머지 페이지들 ---
    
   
    
    @GetMapping("/company-management")
    public String companyManagement(Model model) {
        // 1. 게시글 리스트 (빈 리스트)
        model.addAttribute("boardList", new ArrayList<>()); 
        
        // 2. 페이지네이션 (임시 객체 또는 Map)
        // Pagination 클래스가 있다면 new Pagination()을 쓰시고, 없다면 아래처럼 Map 사용
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", 1);
        pagination.put("startPage", 1);
        pagination.put("endPage", 1);
        pagination.put("maxPage", 1);
        pagination.put("prevPage", 1);
        pagination.put("nextPage", 1);
        model.addAttribute("pagination", pagination);
        
        // 3. 게시판 코드
        model.addAttribute("boardCode", "1");

        return "company-management";
    }
    
    @GetMapping("/status")
    public String status() {
        return "status";
    }
    
    @GetMapping("/tracking")
    public String tracking() {
        return "tracking";
    }
    @GetMapping("/loss")
    public String loss(Model model, @RequestParam(value="cp", defaultValue="1") int cp) {
        
        // 1. 임시 데이터 생성 (분실 신고 내역 35개)
        List<Map<String, Object>> allLosses = new ArrayList<>();
        for(int i = 1; i <= 35; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("trackingNumber", "PKG-2024-" + String.format("%03d", i));
            map.put("type", i % 2 == 0 ? "파손" : "분실"); // 짝수는 파손, 홀수는 분실
            map.put("status", i % 3 == 0 ? "completed" : "investigating"); // 3의 배수는 처리완료
            map.put("date", "2024-01-" + String.format("%02d", (i % 30) + 1));
            allLosses.add(map);
        }
        
        // 2. 리스트 역순 정렬 (최신글이 위로 오게)
        Collections.reverse(allLosses);

        // 3. 페이지네이션 객체 생성
        Pagination pagination = new Pagination(cp, allLosses.size());

        // 4. 현재 페이지 리스트 자르기
        int startIndex = (cp - 1) * pagination.getLimit();
        int endIndex = Math.min(startIndex + pagination.getLimit(), allLosses.size());
        
        List<Map<String, Object>> currentList = allLosses.subList(startIndex, endIndex);

        // 5. Model에 담기
        model.addAttribute("lossList", currentList);
        model.addAttribute("pagination", pagination);

        return "loss";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/error")
    public String errorPage() {
        return "500errorpage";
    }
}