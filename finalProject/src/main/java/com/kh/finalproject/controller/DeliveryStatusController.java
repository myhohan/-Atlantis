package com.kh.finalproject.controller;

import java.util.Map;

import org.springframework.stereotype.Controller; // [중요] RestController -> Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.service.DeliveryStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@Controller // [수정] @RestController를 @Controller로 반드시 변경!
@RequestMapping("deliverystatus") 
@RequiredArgsConstructor
public class DeliveryStatusController {

    private final DeliveryStatusService service;

    @GetMapping("list")
    public String deliveryList(@RequestParam(value = "cp", defaultValue = "1") int cp,
                               Model model,
                               @RequestParam Map<String, Object> paramMap) {
        
        log.info(">>>> [검색어 확인] query : " + paramMap.get("query"));
        
        // 데이터 조회
        Map<String, Object> map = service.selectDeliveryList(paramMap, cp);
        
        // 모델에 데이터 담기
        model.addAttribute("pagination", map.get("pagination"));
        model.addAttribute("boardList", map.get("deliveryList")); 
        
        // [중요] templates/status.html 파일을 찾아가게 됩니다.
        // 만약 파일이 templates/deliverystatus/status.html 이라면 "deliverystatus/status"로 리턴하세요.
        return "status"; 
    }
}
    
    

    