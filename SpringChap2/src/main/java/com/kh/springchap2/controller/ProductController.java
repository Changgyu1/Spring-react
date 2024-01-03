package com.kh.springchap2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap2.model.Product;
import com.kh.springchap2.service.ProductService;


@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/item")
	public ResponseEntity<List<Product>> getAllProduct(){
		List<Product> products = productService.getAllProduct();
		return ResponseEntity.ok(products);
	}
}
