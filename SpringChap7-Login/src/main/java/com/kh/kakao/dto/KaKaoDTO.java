package com.kh.kakao.dto;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KaKaoDTO {
	
	private long id;
	private String email;
	private String nickname;

}
