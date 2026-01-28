package com.kh.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	// /api로 시작하지 않는 모든 경로는 index.html로 포워딩 (리액트가 처리하도록)
	@GetMapping(value = {"/{path:[^\\.]*}", "/**{path:[^\\.]*}"})
	public String forward() {
		return "forward:/index.html";
	}
}
