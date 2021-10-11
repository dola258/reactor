package com.cos.naverrealtime.domain;

import java.sql.Timestamp;	

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cos.naverrealtime.domain.News;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "news_naver")
public class News {
	@Id
	private String _id;
	
	private String title;
	private String company;
	private Timestamp createAt; //날짜타입으로 몽고DB save()하면 무조건 미국 시간(+9)으로 들어감!!
}                            // String은 그대로 들어감
