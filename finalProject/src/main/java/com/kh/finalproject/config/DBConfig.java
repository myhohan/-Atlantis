package com.kh.finalproject.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/config.properties")
public class DBConfig {

    @Autowired
    private ApplicationContext applicationContext;

    // 1. HikariCP 설정
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    // 2. DataSource 설정
    @Bean
    public DataSource dataSource(HikariConfig config) {
        return new HikariDataSource(config);
    }

    // 3. MyBatis SqlSessionFactory 설정
    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        
        // [핵심] Mapper XML 파일 경로 지정
        // src/main/resources/mapper 폴더 아래의 모든 .xml 파일을 읽어옵니다.
        sessionFactoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml")
        );
        
        // DTO 패키지 별칭 설정
        sessionFactoryBean.setTypeAliasesPackage("com.kh.finalproject");
        
        // MyBatis 설정 파일 경로 지정
        sessionFactoryBean.setConfigLocation(
            applicationContext.getResource("classpath:/mybatis-config.xml")
        );
        
        return sessionFactoryBean.getObject();
    }

    // 4. SqlSessionTemplate 설정
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    // 5. 트랜잭션 매니저 설정
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}