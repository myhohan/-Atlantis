package com.kh.finalproject.controller;

import java.util.Map;

import org.springframework.stereotype.Controller; // [중요] RestController -> Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller // [수정 1] 화면을 보여주려면 Controller여야 함
@RequestMapping("deliverystatus") // 또는 "board" (HTML 경로에 맞게 수정 필요)
@RequiredArgsConstructor
public class DeliveryStatusController {

    private final DeliveryStatusService service;

    /**
     * 배송 현황 페이지 이동 및 목록 조회 (페이지네이션 포함)
     * URL: /deliverystatus/list?cp=1
     */
    @GetMapping("list") // [수정 2] 화면 요청은 보통 GET 방식 사용
    public String deliveryList(@RequestParam(value = "cp", defaultValue = "1") int cp,
                               Model model,
                               @RequestParam Map<String, Object> paramMap) {
        
        // [수정 3] 서비스에서 목록 + 페이지네이션 객체(Pagination)를 한 번에 받아옴
        // 서비스 메서드 이름은 예시입니다. (selectDeliveryList 등)
        Map<String, Object> map = service.selectDeliveryList(paramMap, cp);
        
        // [수정 4] HTML에서 사용할 이름("pagination", "boardList")으로 모델에 담음
        model.addAttribute("pagination", map.get("pagination"));
        model.addAttribute("boardList", map.get("deliveryList")); // HTML 변수명(boardList)과 맞춰줌
        
        // [수정 5] HTML 파일 경로 반환 (templates/deliverystatus/list.html 인 경우)
        return "status"; 
    }
}
    