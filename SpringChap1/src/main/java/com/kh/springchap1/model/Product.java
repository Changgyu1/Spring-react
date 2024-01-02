package com.kh.springchap1.model;

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
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="products_seq")
	@SequenceGenerator(name="products_seq", sequenceName="products_seq", allocationSize=1)
	private Long id;
	private String name;
	private String price;
	
}
