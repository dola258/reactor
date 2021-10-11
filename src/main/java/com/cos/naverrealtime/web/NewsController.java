package com.cos.naverrealtime.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.naverrealtime.domain.News;
import com.cos.naverrealtime.domain.NewsRepository;
import com.cos.naverrealtime.util.NaverCraw;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;


@RequiredArgsConstructor
@RestController
public class NewsController {

	private final NewsRepository newsRepository;
	private final NaverCraw naverCraw;
	
//	@CrossOrigin // 서버는 다른 도메인의 자바스크립트 요청을 거부한다(허용해주는 어노테이션)
	@GetMapping(value = "/news", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<News> findAll() {
		return newsRepository.mFindAll()
				.subscribeOn(Schedulers.boundedElastic()); // SSE 연결
 

	}
	


}
