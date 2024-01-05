package com.kh.springchap4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.springchap4.model.UserSNS;

public interface UserRepository extends JpaRepository<UserSNS, Long>{

}
