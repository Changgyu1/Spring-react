package com.kh.springchap5.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springchap5.model.Member;

@Mapper
public interface MembersMapper {
	// 등록하기
	void insertMember(Member member);
	
	// 전체조회
	List<Member> getAllMembers();
	
	// 상세조회
	Member getMemberById(Long id);
}
