package com.kh.springchap1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap1.model.Product;
import com.kh.springchap1.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins="http://localhost:3000", allowCredentials = "true")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	// 상품 조회
	@GetMapping("/item")
	public ResponseEntity<List<Product>> getAllProducts(){
		//return productRepository.findAll();
		List<Product> products = productRepository.findAll();
		return ResponseEntity.ok(products);
	}
	
	
	// 상품 추가
	@PostMapping("/add")
	public ResponseEntity<Product> createProduct(@RequestBody Product newProduct){
		Product createProduct = productRepository.save(newProduct);
		return ResponseEntity.ok(createProduct);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id){
		productRepository.deleteById(id);
		return ResponseEntity.ok("성공적으로 삭제했습니다."); 
	}
	
	@PostMapping("/save/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatePrdocut){
		Product exitProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디를 찾을 수 없음" + id));
		exitProduct.setName(updatePrdocut.getName());
		exitProduct.setName(updatePrdocut.getPrice());
		Product updatedProduct = productRepository.save(exitProduct);
		return ResponseEntity.ok(updatedProduct);
	}

}