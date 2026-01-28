package com.kh.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages =  {
"com.kh.finalproject",
})
public class FinalProjectApplication {
	/* 서버 구동을 위한 todo 어플리케이션 */
	public static void main(String[] args) {
		SpringApplication.run(FinalProjectApplication.class, args);
	}

}
