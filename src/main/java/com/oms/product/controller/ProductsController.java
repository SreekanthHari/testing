package com.oms.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.oms.product.dto.ProductDTO;
import com.oms.product.dto.QuantityDTO;
import com.oms.product.service.ProductService;

@RestController
@CrossOrigin
public class ProductsController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProductService productService;
	
	@Autowired
	Environment environment;
	//Finding all Products
	@GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDTO> getAllProducts() {
		logger.info("====ProductController : Fetching all products====");
		return productService.getAllProducts();
	}
	
	@GetMapping(value = "/products/id/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
		logger.info("===ProductController : Fetching details of product with productId {}", productId);
		try {
			ProductDTO productDTO =  productService.getProductById(productId);
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}

	@GetMapping(value = "/products/name/{productName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO getProductByName(@PathVariable String productName) {
		logger.info("===ProductController : Fetching details of productName {}", productName);
		return productService.getProductByName(productName);
	}
	
	@GetMapping(value = "/products/category/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDTO> getProductsByCategory(@PathVariable String category) {
		logger.info("===ProductController : Fetching all products by category {}", category);
		return productService.getProductByCategory(category);
	}
	
	@PostMapping(value = "/seller/{sellerId}/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProduct(@PathVariable Long sellerId, @RequestBody ProductDTO productDTO) {
		logger.info("====Product Creation Request by seller {} for product with productId {}", sellerId, productDTO);
		try {
			productService.addProduct(sellerId, productDTO);
			String msg = environment.getProperty("PRODUCT.SUCCESS") + " " + productDTO.getProdId();
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()), e);
		}
	}
	
	@PutMapping(value = "/seller/{sellerId}/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object updateStock(@PathVariable Long sellerId, @PathVariable Long productId, @RequestBody QuantityDTO quantity) {
		logger.info("====Product Stock Updation Request by seller {} for product with productId {}", sellerId, productId);
		//System.out.println(quantity);
		return productService.updateStock(productId, quantity.getQuantity());
	}
	
	@DeleteMapping(value = "/seller/{sellerId}/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteProduct(@PathVariable Long sellerId, @PathVariable Long productId) {
		logger.info("======Product Deletion Request by seller {} with product {}", sellerId, productId);
		productService.deleteProductById(productId);
	}
	
}
