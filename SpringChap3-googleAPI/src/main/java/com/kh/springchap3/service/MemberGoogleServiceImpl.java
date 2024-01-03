package com.kh.springchap3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.springchap3.model.MemberGoogle;
import com.kh.springchap3.repository.MemberGoogleRepository;

@Service
public class MemberGoogleServiceImpl implements MemberGoogleService{
	private final MemberGoogleRepository memberGoogleRepository;
	
	@Autowired
	public MemberGoogleServiceImpl(MemberGoogleRepository memberGoogleRepository) {
		this.memberGoogleRepository = memberGoogleRepository;
	}
	
    @Override
    public MemberGoogle findByUsername(String username) {
        return memberGoogleRepository.findByMyname(username);
    }

    @Override
    public void saveUser(MemberGoogle user) {
    	memberGoogleRepository.save(user);
    }
}
