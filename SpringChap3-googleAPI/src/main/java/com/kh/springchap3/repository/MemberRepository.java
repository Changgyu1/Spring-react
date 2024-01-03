package com.kh.springchap3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springchap3.model.MemberGoogle;

public interface MemberRepository extends JpaRepository<MemberGoogle,Long>{
	
	// 추가적으로 필요한 메서드 작성
	Optional<MemberGoogle> findByMyname(String username);
}
