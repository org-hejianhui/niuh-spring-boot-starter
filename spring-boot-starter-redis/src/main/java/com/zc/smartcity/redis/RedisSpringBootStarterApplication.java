package com.zc.smartcity.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class RedisSpringBootStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSpringBootStarterApplication.class, args);
	}
}
