package com.cos.naverrealtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NaverRealtimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverRealtimeApplication.class, args);
	}

}
