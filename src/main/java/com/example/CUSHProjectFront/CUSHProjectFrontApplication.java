package com.example.CUSHProjectFront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@EnableScheduling
@SpringBootApplication
public class CUSHProjectFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(CUSHProjectFrontApplication.class, args);
	}

}
