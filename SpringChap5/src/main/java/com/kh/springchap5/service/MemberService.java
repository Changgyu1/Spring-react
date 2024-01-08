package com.kh.springchap5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.springchap5.mapper.MembersMapper;
import com.kh.springchap5.model.Member;

@Service
public class MemberService {
	
	@Autowired
	private MembersMapper memberMapper;
	
	// 회원 정보 가져오기
	public void signUpMember(Member member) {
		memberMapper.insertMember(member);
	}
	
	// 전체조회
	public List<Member> getAllMembers(){
		return memberMapper.getAllMembers();
	}
		
		// 상세조회
	public	Member getMemberById(Long id) {
		return memberMapper.getMemberById(id);
	}
}
