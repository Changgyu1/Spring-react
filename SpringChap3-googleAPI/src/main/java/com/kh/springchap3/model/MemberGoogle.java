package com.kh.springchap3.model;

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
@Table(name="memberGoogle")
public class MemberGoogle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mg_seq")
	@SequenceGenerator(name="mg_seq", sequenceName = "mg_seq", allocationSize = 1)
	private Long id;
	private String email;
	private String myname;
	
	



}
