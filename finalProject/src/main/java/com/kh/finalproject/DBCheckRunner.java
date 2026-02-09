package com.kh.finalproject; // 패키지명은 본인 프로젝트에 맞게 수정!

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DBCheckRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DBCheckRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("================= [DB 접속 진단 시작] =================");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. 접속 주소 확인
            System.out.println("1. 접속 DB 주소 (URL): " + conn.getMetaData().getURL());

            // 2. 접속 계정 확인
            ResultSet userRs = stmt.executeQuery("SELECT USER FROM DUAL");
            if (userRs.next()) {
                System.out.println("2. 현재 접속 계정 (User): " + userRs.getString(1));
            }

            // 3. 테이블 존재 여부 확인
            ResultSet tableRs = stmt.executeQuery("SELECT count(*) FROM USER_TABLES WHERE TABLE_NAME = 'TB_AUTH_KEY'");
            if (tableRs.next()) {
                int count = tableRs.getInt(1);
                System.out.println("3. TB_AUTH_KEY 테이블 개수: " + count);
                if (count == 0) {
                    System.out.println("   🚨 [충격] 스프링이 접속한 곳에는 테이블이 없습니다!");
                    System.out.println("   (DB 도구와 스프링이 서로 다른 DB를 보고 있습니다.)");
                } else {
                    System.out.println("   ✅ [정상] 테이블이 보입니다! (이러면 에러가 안 나야 정상)");
                }
            }
        } catch (Exception e) {
            System.out.println("🚨 DB 연결 실패: " + e.getMessage());
        }
        System.out.println("================= [DB 접속 진단 종료] =================");
    }
}