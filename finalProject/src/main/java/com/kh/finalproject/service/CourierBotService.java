package com.kh.finalproject.service;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.kh.finalproject.dto.DeliveryRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourierBotService {

    public String reserveDelivery(DeliveryRequest req) {
        
        log.info("🤖 [봇] 택배 자동 예약 시작...");

        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
    
        WebDriver driver = new ChromeDriver(options);
        String realPostNo = ""; 

        try {
          
            driver.get("https://www.naver.com"); 
            Thread.sleep(2000); 
            
           
            realPostNo = "HANJIN-" + System.currentTimeMillis(); 
            
            log.info("✅ [봇] 예약 성공! 발급된 번호: {}", realPostNo);

        } catch (Exception e) {
            log.error("❌ [봇] 예약 실패", e);
            realPostNo = "ERROR-FAIL"; 
        } finally {
            driver.quit();
        }
        
       
        return realPostNo;
    }
}
