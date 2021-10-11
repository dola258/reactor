package com.cos.naverrealtime.batch;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.naverrealtime.NaverRealtimeApplication;
import com.cos.naverrealtime.domain.NewsRepository;
import com.cos.naverrealtime.util.NaverCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsBatch {
	
	private final NewsRepository newsRepository;
	private final NaverCraw naverCraw;
	private final NaverRealtimeApplication naverRealtimeApplication;

	// 초, 분, 시, 일, 월, 주
//	@Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
	@Scheduled(cron = "0 27 8 * * *", zone = "Asia/Seoul")
//	@Scheduled(fixedDelay = 1000)
	public void newsCrawAndSave() throws Exception {
	
		naverRealtimeApplication.run();
	}
}