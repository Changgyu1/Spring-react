package com.kh.springchap5.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private Long memberID;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private Date registrationDate;
}
