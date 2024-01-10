package com.kh.kakao.service;

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

import com.kh.kakao.dto.KaKaoDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;





@Service
@AllArgsConstructor
@NoArgsConstructor
public class KaKaoService {
	
	
	@Value("${kakao.client.id}")
	private String KAKAO_CLIENT_ID;
	
	@Value("${kakao.client.secret}")
	private String KAKAO_CLIENT_SECRET;
	
	// value를 썼기 때문에 각 값을 변수에 넣어서 보관하겠다는 의미
	@Value("${kakao.redirect.url}")
	private String KAKAO_REDIRECT_URL;
	
	// URI는 URL보다 큰 범위 URL은 URI안에있는 링크일 뿐
	// 카카오 자체에서 인증으로 들어가는 공식 주소(URI)
	private final static String KAKAO_AUTH_URI="http://kauth.kakao.com";
	
	// 카카오 자체에서 API로 들어가는 공식 주소
	private final static String KAKAO_API_URI="https://kapi.kakao.com";
	
	public String getKakaoLogin() { // 카카오개발자 공식문서에 있는 주소
		return KAKAO_AUTH_URI + "/oauth/authorize?client_id="+ KAKAO_CLIENT_ID + "&redirect_uri=" + KAKAO_REDIRECT_URL + "&response_type=code";
	}
	
	public KaKaoDTO getKakaoInfo(String code) throws Exception{
		
		if(code == null) throw new Exception("존재하는 인증 코드가 없습니다.");
		
		// 로그인이 허용된 토큰이 들어갈 공간
		String accessToken="";
		// 만약에 토큰이 재발급된다면 재발급된 토큰이 들어갈 공간
		String refreshToken="";
		
		// http HEADER에 내 정보를 흘려 보내겠다 작성
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/x-www-form-urlencoded");
			/*
			카카오 로그인 아이디 + 시크릿키 + 코드 + 리다이렉트 url 모두 붙여줄 것
			MultiValueMap String에서 제공하는 인터페이스
			여러 개의 값을 하나에 키에 연결할 수 있도록 합쳐주는 역할
			http 에서 요청이 여러개일 때 자주 사용
			<String, String> 인 이유는 key, value를 모두 각각 String으로 넣을 것이기 때문
			MultiValueMap<key, value> key와 value를 String으로 설정해서
			카카오톡에서 설정한 값을 적고 그 값에 대한 내용물을 갖고오겠다는 의미
			예를 들어
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			param.add("key1","value1");
			param.add("key1","value2");
			param.add("key2","value3");
			
			결과
			key1 -> [value1, value2]
			key2 -> [value3]
			*/
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("grant_type", "aothorization_code");
			params.add("client_id", KAKAO_CLIENT_ID);
			params.add("client_secreat", KAKAO_CLIENT_SECRET);
			params.add("code", code);
			params.add("redirect", KAKAO_REDIRECT_URL);
			
			// Spring에서 제공하는 것
			// RestTemplate 사용해서 HTTP에 요청을 보내고
			// 요청에 대한 응답을 받아오는 템플릿
			// HTTP 요청을 생성하고 서버에 전달해주는 역할
			RestTemplate restTemplate = new RestTemplate();
			
			// HTTP 요청이나 응답의 헤더 본문 http 메서드를 포함하는 엔터티
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params,headers);
			
			ResponseEntity<String> response = restTemplate.exchange(
					// 요청보낼 URI
					// 카카오 OAuth 토큰을 얻기 위해서
					// /oauth/token POST 요청을 보냄
					KAKAO_AUTH_URI + "/oauth/token",
					HttpMethod.POST,
					httpEntity, // 본문에서 요청하고자 하는 내용과 헤더 정보를 포함하는 객체
					String.class // 서버에서 오는 응답을 String 형태로 받아오겠다는 의미
					);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
			
			accessToken = (String) jsonObj.get("access_token");
			refreshToken = (String) jsonObj.get("refresh_token");
		} catch (Exception err) {
			throw new Exception("api를 불러오지 못했습니다.");
		}
		return getUserInfoWithToken(accessToken);
	}
	
	// 카카오에서 회사에게 로그인 할 수 있도록 허용 받은 로그인 허용 토큰을 사용해서
	// 카카오 API에서 사용자 정보를 가져오는 메서드
	// 
	private KaKaoDTO getUserInfoWithToken(String accessToken) throws Exception {
		// 토큰용 HTTPHeader 생성
		HttpHeaders headers = new HttpHeaders();
		// Bearer : HTTP 요청에서 인증할 때 특정 형태로 변환해서
		// 토큰 타입을 나타내는 것
		headers.add("Authorization", "Bearer" + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// 내용을 담아놓을 템플릿 생성
		RestTemplate rt = new RestTemplate();
		HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = rt.exchange(
				KAKAO_API_URI + "/v2/user/me",
				HttpMethod.POST,
				httpEntity,
				String.class
				);
				
		// Response 데이터를 가지고오기
		JSONParser jsonParset = new JSONParser();
		JSONObject jsonObj = (JSONObject) jsonParset.parse(response.getBody());
		JSONObject account = (JSONObject) jsonObj.get("kakao_account");
		JSONObject profile = (JSONObject) account.get("profile");
		
		long id = (long) jsonObj.get("id");
		String email = String.valueOf(account.get("email"));
		String nickname = String.valueOf(profile.get("nickname"));
		
		return KaKaoDTO.builder()
				.id(id)
				.email(email)
				.nickname(nickname).build();
	}
}
