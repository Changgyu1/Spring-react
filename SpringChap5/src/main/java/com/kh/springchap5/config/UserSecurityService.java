package com.kh.springchap5.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.kh.springchap5.mapper.MembersMapper;
import com.kh.springchap5.model.Member;

import lombok.RequiredArgsConstructor;

@Configuration
@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService{
	/*

	데이터베이스나 외부에서 로그인하여 인증을 하기 위해서는 인증 처리를 해야함

	UserDetailsService : 사용자 정보를 인증

	*/
	
	private final MembersMapper membersMapper;

	
	
	//Required없으면 써라
	//public UserSecurityService(MembersMapper membersMapper) {
		//this.membersMapper= membersMapper;
	//}
	

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member _siteUser = membersMapper.loginMember(username);
		if(_siteUser == null) {
			System.out.println("실패 : " + _siteUser);
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		System.out.println("성공 : " + _siteUser.getUsername());
		// 만약에 admin user로 로그인이 된다면 로그인 분류를 role에 따라 추가로 작성
		if("admin_Haeun".equals(_siteUser.getUsername())) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new User	(_siteUser.getUsername(), _siteUser.getPassword(), authorities);
	}

	
}