package com.kh.springchap5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.springchap5.mapper.MembersMapper;
import com.kh.springchap5.model.Member;

@Service
public class MemberService {
	@Autowired
	private MembersMapper membersMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	//회원 정보 저장하기
	public Member signUpMember(String username, String password, String fullName, String email) {
		
		Member member = new Member();
		
		member.setUsername(username);
		member.setPassword(passwordEncoder.encode(password));
		member.setFullName(fullName);
		member.setEmail(email);
		membersMapper.insertMember(member);
		return member;
	}
	
	// 회원 정보 가져오기
	public void signUpMember(Member member) {
		membersMapper.insertMember(member);
	}
	
	// 전체조회
	public List<Member> getAllMembers(){
		return membersMapper.getAllMembers();
	}
		
		// 상세조회
	public	Member getMemberById(Long id) {
		return membersMapper.getMemberById(id);
	}
	public void login(String username) {
		membersMapper.loginMember(username);
	}
}
