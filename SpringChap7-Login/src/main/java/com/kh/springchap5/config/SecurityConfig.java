package com.kh.springchap5.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		System.out.println("성공");
		http
		.authorizeHttpRequests((authorizeHttpRequests)-> authorizeHttpRequests
				.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		.formLogin((formLogin)->formLogin
			.loginPage("/members/login")
			.defaultSuccessUrl("/members/home"))
		.logout((logout)-> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true))
		;
		return http.build();
		
	}
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration a) throws Exception{
		return a.getAuthenticationManager();
	}
	
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 기본형식인 Security는 기본적으로 DelegatingPasswordEncoder를 BCryptPasswordEncoder() 형식으로 저장
	}
	

}