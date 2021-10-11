package com.cos.naverrealtime.batch;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.naverrealtime.domain.News;
import com.cos.naverrealtime.domain.NewsRepository;
import com.cos.naverrealtime.util.NaverCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsBatch {
	
	private final NewsRepository newsRepository;
	private final NaverCraw naverCraw;

	// 초, 분, 시, 일, 월, 주
//	@Scheduled(cron = "*/30 * * * * *", zone = "Asia/Seoul")
	@Scheduled(fixedDelay = 1000)
	@Bean
	public void newsCrawAndSave() {
	
		List<News> newsList = naverCraw.collect();
		System.out.println(newsList);
	
	
		newsRepository.saveAll(newsList); 
	}
}