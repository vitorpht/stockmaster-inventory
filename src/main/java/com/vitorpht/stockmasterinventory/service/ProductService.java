package com.vitorpht.stockmasterinventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorpht.stockmasterinventory.model.Product;
import com.vitorpht.stockmasterinventory.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	public List<Product> searchProducts(String search) {
		return productRepository.findByNameContainingIgnoreCase(search);
	}

	public List<Product> getProductsByCategory(Long categoryId) {
		return productRepository.findByCategoryId(categoryId);
	}

	public long getTotalProducts() {
		return productRepository.count();
	}

	public int getTotalStockQuantity() {
		return productRepository.findAll().stream()
				.mapToInt(Product::getQuantity)
				.sum();
	}

}
