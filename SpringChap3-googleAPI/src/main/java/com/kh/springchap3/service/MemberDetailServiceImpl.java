package com.kh.springchap3.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.springchap3.model.MemberGoogle;
import com.kh.springchap3.repository.MemberRepository;

@Service
public class MemberDetailServiceImpl implements UserDetailsService {
	  @Autowired
	    private MemberRepository memberRepository;

	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        MemberGoogle user = memberRepository.findByMyname(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

	        return new org.springframework.security.core.userdetails.User(
	                user.getMyname(),
	                "",
	                Collections.emptyList());
	    }
}
