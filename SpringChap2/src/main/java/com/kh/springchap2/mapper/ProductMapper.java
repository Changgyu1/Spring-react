package com.kh.springchap2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.springchap2.model.Product;

@Mapper
public interface ProductMapper {
	// src/main/resources/mapper/ProductMapper.xml
	// 밑에 작성해준 SQL 문 id만 작성할 것
	List<Product> getAllProduct();
}
