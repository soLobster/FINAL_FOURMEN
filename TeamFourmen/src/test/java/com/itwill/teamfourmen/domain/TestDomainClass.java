package com.itwill.teamfourmen.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestDomainClass {
	
	// 공공 API 인증키 및 전체 풀 주소
	// 변수에 여러 값을 넣어서 주소 체계를 만들어야 한다면 -> StringBuilder를 사용.
	// String : 불변(immutable)성을 가지므로 문자열을 더할 때 매번 새로운 객체를 생성해서 참조하는 방식 -> 값 변경X
	// StringBuilder : 문자열을 더해 나갈 때 새로운 객체를 매번 생성하는 것이 아니라 기존 데이터 값에 추가해가는 방식. 속도가 빠름.
	// 				 : mutable 속성이고, append(), insert(), delete() 등을 사용해서 값을 변경.
	// 보통 공공 API 방식 -> StringBuilder 사용.
//	String url = "https://api.themoviedb.org/3";
//	String apiKey = "98284f10b710f15194cd80069283a0aa";
//	String endpoint = "/movie/now_playing";
//	String testFullUrl = url + endpoint + "?api_key=" + apiKey;
	
	// TEST는 여기서!
	
//	@Test
	public void test1() throws IOException {
		
//		// 1. URL 생성자로 URL 객체 만들기.
//		// java.net.URL: 자바에서 url을 다루는 클래스.
//		URL url = new URL(testFullUrl);
//		
//		try {
//			// 2. HTTP Connection 구하기 - URLConnection openConnection()
//			// java.net.HttpURLConnection은 URLConnection을 구현한 추상 클래스. URLConnection은 웹을 통해 데이터를 주고 받는데 사용.
//			// openConnection() 메서드는 URL을 참조하는 객체를 URLConnection 객체로 반환.
//			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection(); 
//			
//			// 3. Request 방식 설정하기 - void setRequestMethod(String method)
//			// 기본 설정은 GET 요청. GET / POST / HEAD / OPTIONS / PUT / DELETE / TRACE 중 한 개를 골라서 요청 가능.
//			urlConn.setRequestMethod("GET");
//			
//			// 4. Request 속성 값 설정하기 - void setRequestProperty(String method)
//			// JSON 형식의 데이터를 받으려면 Content-type의 값을 "application/json"으로 설정해야 함.
//			// 또한 X-Auth-Token이나 Authorization값 설정이 필요하다면 해당 메서드를 사용하여 값을 설정하면 됨.
//			urlConn.setRequestProperty("Content-type", "application/json");
//			
//			// 중간 테스트...
//			// 200(ok)이 들어왔는지 확인(요청이 제대로 들어갔다면 200이 옴)
//			log.info("========================================");
//			log.info("응답 결과 출력(200이면 성공) --> Response code: " + urlConn.getResponseCode());
//			log.info("========================================");
//			
//			// 5. 출력 가능 상태로 설정하기 - void setDoOutput(boolean dooutput)
//			// 받아온 JSON 데이터를 출력 가능한 상태(true)로 변경해줘야 함. 기본값은 false.
//			urlConn.setDoOutput(true);
//			
//			// 버퍼 이용:
//			// BufferedReader -> 버퍼를 이용해서 읽고 쓰는 함수.
//			// 버퍼(Buffer): 완충제 역할.
//			//			   : 입출력 속도 향상을 위해서 데이터를 일시적으로 메모리 영역의 한 곳에 모아두는 것.
//			// 버퍼 장점: 버퍼를 이용하면 입출력 관력 처리 작업을 매우 빠르게 할 수 있음.
//			// 6. 입력 스트림으로 데이터 읽기.
//			// 인코딩 방식은 InputStreamReader, 생성자 두 번째 인자값(charsetName)에 "UTF-8"로 설정.
//			StringBuffer sb = new StringBuffer();
//			try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"))) {
//				String inputLine;
//				while ((inputLine = br.readLine()) != null) {
//					sb.append(inputLine);
//					log.info("========================================");
//					log.info("result={}", inputLine);
//					log.info("========================================");
//				}
//				log.info("API 응답 데이터: " + sb.toString());
//			} catch (Exception e) {
//				log.error("API 호출 중 오류 발생:", e);
//			} finally {
//				if (urlConn != null) {
//					urlConn.disconnect();
//				}
//			}
//			
//			// 값 출력하기...
//			String jsonResponse = sb.toString();
//			log.info("========================================");
//			log.info("출력 결과 (JSON): " + jsonResponse);
//			log.info("========================================");
//			
//		} catch (Exception e) {
//			log.error("API 호출 중 오류 발생", e);
//		} 
		
	}			

	
}
