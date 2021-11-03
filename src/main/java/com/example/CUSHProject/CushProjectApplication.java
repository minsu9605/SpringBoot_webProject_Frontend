package com.example.CUSHProject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableBatchProcessing //Spring Batch 기능 활성화
@SpringBootApplication
public class CushProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CushProjectApplication.class, args);
	}

}
