package com.kh.springchap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap1.model.User;
import com.kh.springchap1.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> getAllusers(){
		return userRepository.findAll();
	}

	
	/*
	
	@PostMapping : 클라이언트(사용자)가 html에 작성한 정보를 DB에 저장할 수 있도록 도와주는 어노테이션
	ResponseEntity : 응답을 나타내는 클래스
	우리가 자주 봤던 404 400 500 이외에 200이라는 응답이 있음
	200은 성공적으로 데이터를 전송했다는 의미
	ResponseEntity.ok 라는 것은 데이터를 잘 전송했다는 의미이기 떄문에
	ok가 200이라는 내용을 담고 있음
	
	 */
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User newUser){
		User createdUser = userRepository.save(newUser);
		return ResponseEntity.ok(createdUser);
	}
}
