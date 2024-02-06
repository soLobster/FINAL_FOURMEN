package com.itwill.teamfourmen.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.itwill.teamfourmen.dto.PageAndListTestDto;
import com.itwill.teamfourmen.dto.PopularPersonTestDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@SpringBootTest
public class ExampleTest {
	
	@Autowired
	
	private static final Logger log = LoggerFactory.getLogger(ExampleTest.class);
	
	// 공공 API 인증키 및 전체 풀 주소
	// 변수에 여러 값을 넣어서 주소 체계를 만들어야 한다면 -> StringBuilder를 사용.
	// String : 불변(immutable)성을 가지므로 문자열을 더할 때 매번 새로운 객체를 생성해서 참조하는 방식 -> 값 변경X
	// StringBuilder : 문자열을 더해 나갈 때 새로운 객체를 매번 생성하는 것이 아니라 기존 데이터 값에 추가해가는 방식. 속도가 빠름.
	// 				 : mutable 속성이고, append(), insert(), delete() 등을 사용해서 값을 변경.
	// 보통 공공 API 방식 -> StringBuilder 사용.
	String url = "https://api.themoviedb.org/3";
	String endpoint = "/movie/now_playing";
	String apiKey = "98284f10b710f15194cd80069283a0aa";
	String testFullUrl = url + endpoint + "?api_key=" + apiKey;
	
	String URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=98284f10b710f15194cd80069283a0aa";
	
	// TEST는 여기서!
	
//	@Test
	public void test1() throws IOException {
		
		try {
			// 1. URL 생성자로 URL 객체 만들기.
			// java.net.URL: 자바에서 url을 다루는 클래스.
			URL url = new URL(testFullUrl);
			
			// 2. HTTP Connection 구하기 - URLConnection openConnection()
			// java.net.HttpURLConnection은 URLConnection을 구현한 추상 클래스. URLConnection은 웹을 통해 데이터를 주고 받는데 사용.
			// openConnection() 메서드는 URL을 참조하는 객체를 URLConnection 객체로 반환.
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection(); 
			
			// 3. Request 방식 설정하기 - void setRequestMethod(String method)
			// 기본 설정은 GET 요청. GET / POST / HEAD / OPTIONS / PUT / DELETE / TRACE 중 한 개를 골라서 요청 가능.
			urlConn.setRequestMethod("GET");
			
			// 4. Request 속성 값 설정하기 - void setRequestProperty(String method)
			// JSON 형식의 데이터를 받으려면 Content-type의 값을 "application/json"으로 설정해야 함.
			// 또한 X-Auth-Token이나 Authorization값 설정이 필요하다면 해당 메서드를 사용하여 값을 설정하면 됨.
			urlConn.setRequestProperty("Content-type", "application/json");
			
			// 중간 테스트...
			// 200(ok)이 들어왔는지 확인(요청이 제대로 들어갔다면 200이 옴)
			log.info("========================================");
			log.info("응답 결과 출력(200이면 성공) --> Response code: " + urlConn.getResponseCode());
			
			// <--------------------------------------------- 구분선 -------------------------------------------------->
			// 5. 출력 가능 상태로 설정하기 - void setDoOutput(boolean dooutput)
			// 받아온 JSON 데이터를 출력 가능한 상태(true)로 변경해줘야 함. 기본값은 false.
			// POST 방식일 경우에 사용...
			// <--------------------------------------------- 구분선 -------------------------------------------------->
			
			// 버퍼 이용:
			// BufferedReader -> 버퍼를 이용해서 읽고 쓰는 함수.
			// 버퍼(Buffer): 완충제 역할.
			//			   : 입출력 속도 향상을 위해서 데이터를 일시적으로 메모리 영역의 한 곳에 모아두는 것.
			// 버퍼 장점: 버퍼를 이용하면 입출력 관력 처리 작업을 매우 빠르게 할 수 있음.
			// 6. 입력 스트림으로 데이터 읽기.
			// 인코딩 방식은 InputStreamReader, 생성자 두 번째 인자값(charsetName)에 "UTF-8"로 설정.
			// getInputStream(): 
			// -> HttpURLConnection 클래스의 일부이며, URL로 지정된 리소스에서 데이터를 읽기 위해 연결에서 입력 스트림을 얻는 데 사용됨.
			// -> 입력 스트림은 HTTP 요청에 대한 응답으로 서버에서 들어오는 데이터를 의미함.
			// -> 일반적으로 HTTP 요청에서 응답 본문을 검색하려는 경우에 사용됨.
			StringBuffer sb = new StringBuffer();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"))) {
				
				// BufferReader 값 읽어보기...
				log.info("========================================");
				log.info("bufferReader 정체 --> {}", br.toString());
				
				// inputStream 값 읽어보기...
				InputStream inputStream = urlConn.getInputStream();
				log.info("========================================");
				log.info("inputStream 정체 --> {}", inputStream);
				
				// inputStreamReader 값 읽어보기...
				InputStreamReader inputStreamReader = new InputStreamReader(urlConn.getInputStream(), "UTF-8");
				log.info("========================================");
				log.info("inputStreamReader 정체 --> {}", inputStreamReader);
				
				// StringBuffer에 문자열 유형(String) 값을 저장할 수 있음!
				// StringBuffer는 변경 가능하다는 점이 중요한데...
				// 즉, 새 객체를 생성하지 않고도 내용을 수정할 수 있다!
				// 이는 변경할 수 없는 String 클래스와 대조되는데... String은 새 객체를 
				// StringBuffer의 변경 가능 특성으로 인해 문자열 연결이나 수정이 많이 필요한 작업에 더 효율적임.
				// 변수에 여러 값을 넣어서 주소 체계를 만들어야 한다면 -> StringBuilder를 사용.
				// String : 불변(immutable)성을 가지므로 문자열을 더할 때 매번 새로운 객체를 생성해서 참조하는 방식 -> 값 변경X
				// StringBuilder: 문자열을 더해 나갈 때 새로운 객체를 매번 생성하는 것이 아니라 기존 데이터 값에 추가해가는 방식. 속도가 빠름.
				// 				: mutable 속성이고, append(), insert(), delete() 등을 사용해서 값을 변경.
				// 보통 공공 API 방식 -> StringBuilder 사용.
				String response;
				while ((response = br.readLine()) != null) {
					sb.append(response);
					// StringBuffer 값 읽어보기...
					log.info("========================================");
					log.info("StringBuffer 정체 --> {}", sb.toString());
					log.info("========================================");
					log.info("response -----------> {}", response);
				}
				log.info("========================================");
				log.info("API 응답 데이터 ----> {}", sb.toString());
			} catch (Exception e) {
				log.info("========================================");
				log.error("API 호출 중 오류 발생:", e);
			} finally {
				if (urlConn != null) {
					urlConn.disconnect();
				}
			}
			
			// 값 출력하기...
			String jsonResponse = sb.toString();
			log.info("========================================");
			log.info("출력 결과 (JSON) ---> " + jsonResponse);
			
		} catch (Exception e) {
			log.error("API 호출 중 오류 발생", e);
		} 
		
	}
	
	// GPT가 작성한 코드...
//	@Test
	public void test2() throws IOException {
	    HttpURLConnection urlConn = null;
	    try {
	        URL requestUrl = new URL(testFullUrl);
	        urlConn = (HttpURLConnection) requestUrl.openConnection();
	        urlConn.setRequestMethod("GET");
	        urlConn.setRequestProperty("Content-type", "application/json");
	        
	        // 응답이 200(ok)이면 성공...
	        log.info("Response code: " + urlConn.getResponseCode());

	        StringBuilder response = new StringBuilder();
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"))) {
	            String inputLine;
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	        }
	        log.info("API 응답 데이터: " + response.toString());
	    } catch (IOException e) {
	        log.error("API 호출 중 오류 발생", e);
	    } finally {
	        if (urlConn != null) {
	            urlConn.disconnect();
	        }
	    }
	}
	
//	@Test
	public void test3() {
		
		// Get a list of people ordered by popularity.
		String peopleUrl = "https://api.themoviedb.org/3/person/popular?api_key=98284f10b710f15194cd80069283a0aa";
		
		// API 요청 만들기...
		String url = "https://api.themoviedb.org/3/";
		String endPoint = "person/popular?api_key=";
		String key = "98284f10b710f15194cd80069283a0aa";
		
		HttpURLConnection urlConn = null;
		try {
			URL requestUrl = new URL(peopleUrl);
			urlConn = (HttpURLConnection) requestUrl.openConnection();
			urlConn.setRequestMethod("GET");
	        urlConn.setRequestProperty("Content-type", "application/json");
	        
	        // 응답이 200(ok)이면 성공...
	        log.info("===========================================================");
	        log.info("Response code(200이면 성공!): {}", urlConn.getResponseCode());
	        
	        StringBuilder response = new StringBuilder();
	        try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"))) {
	            String inputLine;
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	        }
	        log.info("===========================================================");
	        log.info("API 응답 데이터: " + response.toString());
	        
		} catch (Exception e) {
			log.error("API 호출 중 오류 발생", e);
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}
	
	// GPT가 작성한 코드...
	@Test
	public void test4() {
		String people_lists_popular_url = "https://api.themoviedb.org/3/person/popular?api_key=98284f10b710f15194cd80069283a0aa";
		WebClient webClient = WebClient.create(people_lists_popular_url);
		
		Flux<PageAndListTestDto> responseFlux = webClient.get()
				.retrieve()
				.bodyToFlux(PageAndListTestDto.class);
		
		responseFlux.subscribe(response -> {
			List<PopularPersonTestDto> dto = response.getResults();
			log.info("dto --> {}", dto.get(0).getName());
	        log.info("응답 결과 --> {}", response);
	        log.info("페이지 값 --> {}", response.getPage());
	        log.info("결과값 --> {}", response.getResults().getClass());
	    });
	    
	    // 테스트에 충분한 대기 시간을 주기 위해 sleep을 사용합니다. (실제 코드에서는 피해야 합니다.)
	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	
	} // end test4...
	
}