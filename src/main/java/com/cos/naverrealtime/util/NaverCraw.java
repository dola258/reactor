package com.cos.naverrealtime.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cos.naverrealtime.domain.News;

@Component
public class NaverCraw {
	String startDay = DataStart.standardDay();
	String endDay = DataStart.endDay();
	
	int urlNum = DataStart.urlStart;
	
	public List<News> collect() {

		RestTemplate rt = new RestTemplate();
		List<News> newsList = new ArrayList<>();
		
	

		while(true) {
			String strnum = String.format("%010d", urlNum);
			
			if(strnum.equals("0000300000")) break;
			
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=103&oid=437&aid=" + strnum;
	
			try {
	
				String html = rt.getForObject(url, String.class);
	
				Document doc = Jsoup.parse(html);
	
				// title
				Element titleElement = doc.selectFirst("#articleTitle");
				String title = titleElement.text();
				System.out.println(title);
	
				// company
				Element companyElement = doc.selectFirst(".press_logo a img");
				String companyAttr = companyElement.attr("alt");
				String company = companyAttr;
				System.out.println(company);
	
				// createAt
				Element createAtElement = doc.selectFirst(".t11");
				String createAtText = createAtElement.text() + ":00"; // yyyy.MM.dd. 오전/오후 hh:mm:ss
	
				String date = createAtText.substring(0, 4) + "-" + createAtText.substring(5, 7) + "-"
						+ createAtText.substring(8, 10) + " "; // yyyy-MM-dd
				String dateTime = "";
	
				// 오후, hh:mm:ss가 7자리면 앞에 0을 붙이고 앞 2글자 12더해주기
				if (createAtText.substring(12, 14).equals("오후")) {
					String time = createAtText.substring(15);
					if (time.length() != 8) {
						time = "0" + time;
					}
	
					int beforeHour = Integer.parseInt(time.substring(0, 2));
					int plus = 12;
	
					// 오후 12:13분일 경우 12를 하면 24:13분이됨
					if (beforeHour == 12) {
						dateTime = date + beforeHour + time.substring(2);
					} else {
						int afterHour = beforeHour + plus;
						dateTime = date + afterHour + time.substring(2);
					}
				}
	
				// 오전, hh:mm:ss
				if (createAtText.substring(12, 14).equals("오전")) {
					String time = createAtText.substring(15);
					if (time.length() != 8) {
						time = "0" + time;
					}
					dateTime = date + time;
				}
	
				System.out.println("기사일 : " + dateTime);
				System.out.println("기준시작일 : " + startDay);
				System.out.println("기준마감일 : " + endDay);
				System.out.println("---------------------------------------");
				
				int result = dateTime.compareTo(startDay); // dateTime이 더 크면 양수
				int result2 = dateTime.compareTo(endDay);  // dateTime이 더 작으면 음수
				
				System.out.println("result : " + result);
				System.out.println("result2 : " + result2);
				
				if (result > 0 && result2 < 0) {
					
					SimpleDateFormat sdFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 
					Date createAt = sdFmt.parse(dateTime);
					
					
				//	Timestamp createAt = Timestamp.valueOf(dateTime);
					
					
					// 오브젝트로 저장
					News news = News.builder().title(title).company(company).createAt(createAt).build();
					
					newsList.add(news);
				} 
	
			} catch (Exception e) {
				System.out.println("통신오류");
			}
			urlNum++;
			System.out.println(newsList);
		}
		return newsList;
	}

}

