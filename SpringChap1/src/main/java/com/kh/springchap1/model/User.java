package com.kh.springchap1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usersd_seq")
	@SequenceGenerator(name="user"
			+ "+sd_seq", sequenceName="usersd_seq", allocationSize=1)
	private Long id;
	private String username;
	private String email;
	
}
