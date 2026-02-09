package com.kh.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // ★ 중요: RestController가 아닌 그냥 Controller여야 페이지 이동 가능
public class LostAndFoundPageController {

    // [화면 이동] http://localhost:8080/lostandfound 접속 시 실행
    @GetMapping("/lostandfound")
    public String lostAndFoundPage() {
        return "lostandfound"; // templates/lostandfound.html 파일을 보여줌
    }
}