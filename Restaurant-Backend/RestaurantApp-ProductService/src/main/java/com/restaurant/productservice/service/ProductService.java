package com.restaurant.productservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.restaurant.productservice.exception.FoodTypeNotExistsException;
import com.restaurant.productservice.exception.ProductNotFoundException;
import com.restaurant.productservice.model.CartItemsInfo;
import com.restaurant.productservice.model.CustomResponseEntity;
import com.restaurant.productservice.model.Product;

public interface ProductService {
	public CustomResponseEntity<Object> getAllProduct();
	public CustomResponseEntity<Object> getProductById(long pId) throws ProductNotFoundException;
	public CustomResponseEntity<Object> getProductByFoodType(String type) throws FoodTypeNotExistsException;
	public CustomResponseEntity<Object> getBestsellerProductByFoodType(long pId) throws ProductNotFoundException;
	public CustomResponseEntity<Object> addProduct(Product product);
	public CustomResponseEntity<Object> updateProduct(Product product) throws ProductNotFoundException;
	public CustomResponseEntity<Object> deleteProduct(long pId) throws ProductNotFoundException;
	public CustomResponseEntity<Object> updateLiveStatus(long pId) throws ProductNotFoundException;
	public ResponseEntity<Object> getMultiProductById(List<CartItemsInfo> multiproduct) throws ProductNotFoundException;
}
