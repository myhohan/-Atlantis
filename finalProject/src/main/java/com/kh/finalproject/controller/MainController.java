package com.kh.finalproject.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.kh.finalproject.dto.Member;
import com.kh.finalproject.dto.Pagination; 

@Controller
@RequestMapping("/") 
public class MainController {

   
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
    
    
   
    @GetMapping("/login")
    public String loginPage() {
        return "login"; 
    }
    
    
    
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("member", new Member()); 
        return "signup"; 
    }
    
    
    
   
    
    @GetMapping("/company-management")
    public String companyManagement(Model model) {
       
        model.addAttribute("boardList", new ArrayList<>()); 
        
      
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", 1);
        pagination.put("startPage", 1);
        pagination.put("endPage", 1);
        pagination.put("maxPage", 1);
        pagination.put("prevPage", 1);
        pagination.put("nextPage", 1);
        model.addAttribute("pagination", pagination);
        
        
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
        
        
        List<Map<String, Object>> allLosses = new ArrayList<>();
        for(int i = 1; i <= 35; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("trackingNumber", "PKG-2024-" + String.format("%03d", i));
            map.put("type", i % 2 == 0 ? "파손" : "분실"); 
            map.put("status", i % 3 == 0 ? "completed" : "investigating"); 
            map.put("date", "2024-01-" + String.format("%02d", (i % 30) + 1));
            allLosses.add(map);
        }
        
       
        Collections.reverse(allLosses);

     
        Pagination pagination = new Pagination(cp, allLosses.size());

       
        int startIndex = (cp - 1) * pagination.getLimit();
        int endIndex = Math.min(startIndex + pagination.getLimit(), allLosses.size());
        
        List<Map<String, Object>> currentList = allLosses.subList(startIndex, endIndex);

       
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
    
    @GetMapping("/personalinformationpolicy")
    public String personalPolicy() {
        return "personalinformationpolicy"; 
    }

    @GetMapping("/termsandagreement")
    public String terms() {
        return "termsandagreement"; 
    }

    @GetMapping("/business-info")
    public String businessInfo() {
        return "business-info"; 
    }
}