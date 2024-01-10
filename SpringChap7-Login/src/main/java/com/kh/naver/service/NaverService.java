package com.kh.naver.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.naver.dto.NaverDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Service
@AllArgsConstructor
@NoArgsConstructor
public class NaverService {
	// ${naver.client.id} 은
	// application.properties 에 적혀있는 naver.client.id 를 찾아서
	// naver.client.id 에 설정된 값을 가져오는 것
	// 가지고 온 값을 변수에 담아주는 것
	@Value("${naver.client.id}")
	private String NAVER_CLIENT_ID;
	
	@Value("${naver.client.secret}")
	private String NAVER_CLIENT_SECRET;
	
	@Value("${naver.redirect.url}")
	private String NAVER_REDIRECT_URL;
	
	private final static String NAVER_AUTH_URI = "https://nid.naver.com";
	private final static String NAVER_API_URI = "https://openapi.naver.com";
	
	public String getNaverLogin() {
		return NAVER_AUTH_URI + "/oauth2.0/authorize" + "?client_id=" + NAVER_CLIENT_ID 
				+ "&redirect_uri=" + NAVER_REDIRECT_URL + "&response_type=code";
	}
	
	public NaverDTO getNaverInfo(String code) throws Exception{
		if(code == null) throw new Exception("인증이 되지 않았습니다.");
		
		String accessToken = "";
		String refreshToken = "";
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/x-www-form-urlencoded");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "authorization_code");
			params.add("client_id", NAVER_CLIENT_ID);
			params.add("client_secret", NAVER_CLIENT_SECRET);
			params.add("code", code);
			params.add("redirect_uri", NAVER_REDIRECT_URL);
			
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
			
			ResponseEntity<String> response = restTemplate.exchange(
					NAVER_AUTH_URI + "/oauth2.0/token",
					HttpMethod.POST,
					httpEntity,
					String.class
					);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
			
			accessToken = (String) jsonObj.get("access_token");
			refreshToken = (String) jsonObj.get("refresh_token");
			
			
		} catch(Exception err) {
			throw new Exception("api를 불러올 수 없습니다.");
		}
		return getUserInfoToken(accessToken);
	}
	
	private NaverDTO getUserInfoToken(String accessToken) throws Exception{
		// HttpHeader 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content", "application/x-www-form-urlencoded;charset=utf-8");
		
		// http header 내용 담기
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = rt.exchange(
				NAVER_API_URI + "/v1/nid/me",
				HttpMethod.POST,
				httpEntity,
				String.class
				);
		
		// Response json 데이터 가지고오기
		// JSONParser : JSON 문자열을 Java 객체로 변환하는데 사용하는 클래스
		JSONParser jsonParser = new JSONParser();
		// (JSONObject) jsonParser.parse(response.getBody());
		// reponse에서 받은 HTTP응답 본문 Json 넣어서
		// JSONObject는 key value 형식으로 되어있다
		
		// JSONObject jsonObj 가져온 key value 값에서
		// response만 찾아서 account 안에 넣어준다는 의미
		JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
		JSONObject account = (JSONObject) jsonObj.get("response");
		
		String id = String.valueOf(account.get("id"));
		String email = String.valueOf(account.get("email"));
		String name = String.valueOf(account.get("name"));
		
		return NaverDTO.builder()
				.id(id)
				.email(email)
				.name(name).build();
	}
}
 