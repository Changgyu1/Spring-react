package com.kh.springchap3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.springchap3.model.MemberGoogle;
import com.kh.springchap3.service.MemberGoogleService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("/oauth")
public class OAuthController {

	@Autowired
	private MemberGoogleService memberService;
	
	 @GetMapping("/loginSuccess")
	 public ResponseEntity<String> loginSuccess(@AuthenticationPrincipal OAuth2User oauthUser) {
	     String email = oauthUser.getAttribute("email");
	     MemberGoogle user = memberService.findByUsername(email);
	     System.out.println("OAuth2User : " + oauthUser);
	     System.out.println("이메일 속성 " + email);
	     if (user == null) {
	         user = new MemberGoogle();
	         user.setMyname(email);
	         user.setEmail(email);
	         memberService.saveUser(user);
	         
	         //model.addAttribute("newUser", true);
	     }

	   
	     return ResponseEntity.ok("loginSuccess");
	 }
	     
	  @GetMapping("/logout")
	     public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
	         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	         if (auth != null) {
	             new SecurityContextLogoutHandler().logout(request, response, auth);
	         }
	         return ResponseEntity.ok("로그아웃 됐습니다.");
	     }
    
	
}
