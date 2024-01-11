package com.kh.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	private String memberID;
	private String memberPw;
	private String memberName;
	private String memberDate;
}
