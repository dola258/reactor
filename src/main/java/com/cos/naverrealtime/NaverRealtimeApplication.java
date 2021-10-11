package com.cos.naverrealtime;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cos.naverrealtime.domain.News;
import com.cos.naverrealtime.domain.NewsRepository;
import com.cos.naverrealtime.util.NaverCraw;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class NaverRealtimeApplication implements CommandLineRunner {
	
	private final NewsRepository newsRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(NaverRealtimeApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		NaverCraw naverCraw = new NaverCraw();
		
		List<News> newsList = naverCraw.collect();
		
		Flux.fromIterable(newsList)
			.delayElements(Duration.ofSeconds(2))
			.flatMap(this.newsRepository::save)
			.doOnComplete( ()-> System.out.println("저장"))
			.subscribe();
	}

}
