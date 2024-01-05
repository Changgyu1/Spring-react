package com.kh.springchap4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap4.repository.UserRepository;

//@Controller
/*
@CrossOrigin
각 컨트롤러마다 바라보는 url이 다를 수 있기 때문에
어떤 도메인을 허용해줄지 작성해주는 공간
하나의 도메인이 아니라 다수의 도메인을 넣을 수 있음
origins에 작성해준 도메인의 요청을 허용
allowCredentials : 인증된 사용자의 쿠키를 요청에 포함할 수 있도록 허용할지에 대한 여부
allowedHeaders : 허용할 수 있는 헤더를 지정 * 모든 헤더 허용
 ("X-Requested-With", "Origins", "Content-type", "Accept", "Authorization"));
 methods : 원하는 메서드만 설정해서 받을 수 있도록 한 번 더 처리할 수 있음
 {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
*/
@RestController
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
public class UserController {
	
	@Autowired
	private final UserRepository userRepository;
	

	public UserController (UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "index";
	}
/*
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User principal,
			@RequestParam(value = "naverResponse", required = false)String naverResponse ,Model model) {
		
		System.out.println("OAuth2User Attributes : " + principal.getAttributes());

		String name = null;
		String email = null;
		
		// 만약에 네이버 응답이 널값이 아니라면!
		if(naverResponse != null) {
			// 들어온 naver응답 값을 JSON 형식으로 담을 수 있게
			// JSON 형태를 세팅해주고 JSON안에 Mapper 처리
			JsonNode responseNode;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				responseNode = objectMapper.readTree(naverResponse).get("response");
				
				if(responseNode != null) {
					name = responseNode.get("name").asText();
					email = responseNode.get("email").asText();
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// OAuth2User 에서 이름과 이메일을 추출
		if(name == null || email == null) {
			String principaName = principal.getName();
			
			// principal.getName() 으로 가지고온 정보에서 이메일과 이름만 출력
			// replaceAll -> 문자열에서 공백이나 숫자 등 패턴을 찾을 때 도와주는 식
			String[] keyValue = principaName.replaceAll("[{}]", "").split(",");
			for(String pair : keyValue) {
				String[] entry = pair.split("=");
				if(entry.length == 2) {
					String key = entry[0].trim();
					String value = entry[1].trim();
					if("name".equals(key)) {
						name = value;
					}else if("email".equals(key)) {
						email = value;
					}
				}
			}
			System.out.println(principaName);
		}
		String provider = principal.getName();
		System.out.println("principaName : " + provider);
		// 사용자 정보를 데이터베이스에 저장
		// 1. model
		UserSNS user = new UserSNS();
		user.setName(name);
		user.setEmail(email);
		user.setProvider(provider);

		// 2. repository
		
		// 저장
		userRepository.save(user);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		
		// 모델이 naverResponse 가지고 와야하는 경우 Naver 응답 추가
		model.addAttribute("naverResponse", naverResponse);
		return "loginSuccess";
	}
*/
	// 구글 로그인을 위한 URL 추가
	@GetMapping("/oauth2/authorization/google")
	public String googleLogin() {
		return "redirect:/oauth2/authorization/google";
	}
	@GetMapping("/oauth2/authorization/naver")
	public String naverLogin() {
		return "redirect:/login/oauth2/code/naver";
	}
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("account_email", principal.getAttribute("account_email"));
		model.addAttribute("profile_image", principal.getAttribute("profile_image"));
		model.addAttribute("profile_nickname", principal.getAttribute("profile_nickname"));
		return "loginSuccess";
	}

}
