package com.oms.product.validator;

import java.math.BigDecimal;

import com.oms.product.entity.Product;

public class Validator {

//	public static Boolean validate(Product product) throws Exception{
//		if(validateName(product.getProductName()) && validatePrice(product.getPrice()) && 
//				validateStock(product.getStock()) && validateImage(product.getImage()) && 
//				validateDescription(product.getDescription())) {
//			return true;
//		}
//		return false;
//	}
	
	public static Boolean validateName(String name){
		if((name.length() <= 100) && !name.matches("[ ]*") && name.matches("([A-Za-z])+(\\s[A-Za-z]+)*")) {
			return true;
		}
		return false;
	}
	
	public static Boolean validatePrice(BigDecimal price) {
		int res = price.compareTo(new BigDecimal("200"));
		if(res >= 0) {
			return true;
		}
		return false;
	}
	
	public static Boolean validateStock(Integer stock) {
		if(stock >= 10) {
			return true;
		}
		return false;
	}
	
	public static Boolean validateImage(String image) {
		String regex = ".+(jpeg\"|png\")$";
		if(image.matches(regex)) {
			return true;
		}
		return false;
	}
	
	public static Boolean validateDescription(String desc) {
		if(desc.length() < 500)
			return true;
		return false;
	}
}
