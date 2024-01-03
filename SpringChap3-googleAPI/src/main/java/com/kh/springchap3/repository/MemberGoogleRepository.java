package com.kh.springchap3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springchap3.model.MemberGoogle;

public interface MemberGoogleRepository extends JpaRepository<MemberGoogle, Long>{
	
	MemberGoogle findByMyname(String username);

}
