package com.vitorpht.stockmasterinventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vitorpht.stockmasterinventory.model.Product;
import com.vitorpht.stockmasterinventory.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final StockMovementService stockMovementService;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
	}

	@Transactional
	public Product saveProduct(Product product) {
		boolean isNew = product.getId() == null;
		Product saved = productRepository.save(product);
		if (isNew && saved.getQuantity() > 0) {
			stockMovementService.recordInitialStock(saved);
		}
		return saved;
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
