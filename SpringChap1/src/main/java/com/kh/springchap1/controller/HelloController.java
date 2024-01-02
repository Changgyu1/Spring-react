package com.kh.springchap1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// api로 전달해서 호출
@RestController
@RequestMapping("/api")
// cors : 쿠키나 세션 무언가 접속하는 것을 허용해줄 때 사용
// origins="http://localhost:3000" -> 프론트앤드 주소 값
// allowCredentials = "true" -> 브라우저에서 요청에 대한 응답을 할 때, 요청에 인증 정보(쿠키, HTTP 인증)를 포함할 것인지를 나타내는 것
// 인증 정보를 서로 주고받을 수 있게 해주는 역할을 함
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class HelloController {
	
	@GetMapping("/hello")
	public String getHello() {
		return "Hello Spring";
	}

	@GetMapping("/java")
	public String getJava() {
		return "Hello Spring Java";
	}
}
