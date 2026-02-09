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

    // [수정 1] @Async 삭제! (이제 기다립니다)
    // [수정 2] 리턴 타입을 void -> String으로 변경
    public String reserveDelivery(DeliveryRequest req) {
        
        log.info("🤖 [봇] 택배 자동 예약 시작...");

        // 크롬 옵션 (필요시 --headless 주석 해제)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless"); 
        
        WebDriver driver = new ChromeDriver(options);
        String realPostNo = ""; // 봇이 따온 번호를 담을 변수

        try {
            // (가상) 택배사 접속 및 로직 수행
            driver.get("https://www.naver.com"); // 테스트용
            Thread.sleep(2000); // 2초간 작업하는 척
            
            // 봇이 생성한 진짜 송장번호 (크롤링 결과라고 가정)
            realPostNo = "HANJIN-" + System.currentTimeMillis(); 
            
            log.info("✅ [봇] 예약 성공! 발급된 번호: {}", realPostNo);

        } catch (Exception e) {
            log.error("❌ [봇] 예약 실패", e);
            realPostNo = "ERROR-FAIL"; // 실패 시 표시할 값
        } finally {
            driver.quit();
        }
        
        // [수정 3] 봇이 따온 번호를 밖으로 던져줍니다.
        return realPostNo;
    }
}