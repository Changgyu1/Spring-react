package com.kh.springchap5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.springchap5.model.Member;
import com.kh.springchap5.service.MemberService;

import jakarta.websocket.server.PathParam;


@Controller
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/getAllmembers")
	public String getAllMembers(Model model) {
		model.addAttribute("members",memberService.getAllMembers());
		
		return "membersList";
	}
	
	@GetMapping("/detail/member/{id}")
	public String detailMember(@PathVariable Long id, Model model) {
		Member member = memberService.getMemberById(id);
		model.addAttribute("member", member);
		return "memberDetail";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("member", new Member());
		return "register";
	}
	
	@PostMapping("/register")
	public String registerForm(Member member) {
		memberService.signUpMember(member);
		return "redirect:../../"; // 메인으로 보내기
	}
}
