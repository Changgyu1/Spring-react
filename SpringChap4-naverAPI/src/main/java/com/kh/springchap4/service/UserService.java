package com.kh.springchap4.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.springchap4.model.UserSNS;
import com.kh.springchap4.repository.UserRepository;

public class UserService {
	
	//2. repository
	private final UserRepository userRepository;
	
	@Autowired
	public UserService( UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public void naverLoginService(
			OAuth2User principal, 
			String naverResponse,
			Model model) {
	System.out.println("OAuth2User Attributes : " + principal.getAttributes());
	String name = null;
	String email = null;
	
	//만약에 네이버응답이 들어와서 널값이 아니라면!
	if(naverResponse != null) {
		//들어온 naver응답 값을 Json 형식으로 담을 수 있게 
		//Json 형태를 세팅해주고 Json안에 Mapper처리
		JsonNode responseNode;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			responseNode = objectMapper.readTree(naverResponse).get("response");
			
			if(responseNode!=null) {
				name = responseNode.get("name").asText();
				email = responseNode.get("email").asText();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//OAuth2User 에서 이름과 이메일을 추출
	if(name == null || email == null) {
		String principalName = principal.getName();
		
		//principal.getName() 으로 가지고온 정보에서 이메일과 이름만 출력
		//replaceAll 문자열에서 공백이나 숫자 등 패턴을 찾을 때 와주는 식
		String[] keyValue = principalName.replaceAll("[{}]", "").split(",");
		for(String pair : keyValue) {
			String[] entry = pair.split("=");
			if(entry.length == 2) {
				String key = entry[0].trim();
				String value = entry[1].trim();
				if("name".equals(key)) {
					name = value;
				}else if ("email".equals(key)) {
					email = value;
				}
			}
		}
	}
	String provider = principal.getName();

	System.out.println("UserController 82  ↓ ");
	System.out.println("String provider = principal.getName() :  "+provider);
	//사용자 정보를 데이터베이스에 저장
	//1. model 
	UserSNS user = new UserSNS();
	user.setName(name);
	user.setEmail(email);
	user.setProvider(provider);
	

	//저장
	userRepository.save(user);
	
	model.addAttribute("name",name);
	model.addAttribute("email",email);
	
	//모델이 naverResponse로 가지고 와야하는 경우 Naver 응답 추가
	model.addAttribute("naverResponse", naverResponse);
	}
	
	public void googleLoginService(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("name", principal.getAttribute("name"));
		model.addAttribute("email", principal.getAttribute("email"));
		System.out.println("OAuth2User Attribute : " + principal.getAttributes());
		
	}
}