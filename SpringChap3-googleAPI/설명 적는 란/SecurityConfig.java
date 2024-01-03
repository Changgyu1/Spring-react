package com.kh.springchap3.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				// 모든 요청에 대해 권한 검사를 하지 않고
				// 모든 사용자한테 앤드포인트로 접근할 수 있도록 허용
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		// 다른 포트나 도메인에서 접근할 수 있도록 허용
		// 예를들어 http://localhost:3000 이외 백앤드에서 지정해준 포트 이외
		// 다른 포트도 마찬가지임
		.cors(Customizer.withDefaults())
		// crsf 공격을 방어하기 위한 설정
		.csrf((csrf) -> csrf.disable());
		return http.build();
	}

}
