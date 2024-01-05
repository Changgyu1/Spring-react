package com.kh.springchap4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserSNS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_sns_seq")
	@SequenceGenerator(name="user_sns_seq", sequenceName="user_sns_seq", allocationSize = 1)
	private Long id;
	
	private String name;
	private String email;
	private String provider;
}
