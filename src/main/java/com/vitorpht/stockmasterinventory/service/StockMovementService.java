package com.vitorpht.stockmasterinventory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vitorpht.stockmasterinventory.model.MovementReason;
import com.vitorpht.stockmasterinventory.model.MovementType;
import com.vitorpht.stockmasterinventory.model.Product;
import com.vitorpht.stockmasterinventory.model.StockMovement;
import com.vitorpht.stockmasterinventory.repository.ProductRepository;
import com.vitorpht.stockmasterinventory.repository.StockMovementRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMovementService {

	private final StockMovementRepository stockMovementRepository;
	private final ProductRepository productRepository;

	@Transactional
	public StockMovement registerEntry(Long productId, Integer quantity) {
		validateQuantity(quantity);
		Product product = findProduct(productId);
		product.setQuantity(product.getQuantity() + quantity);
		productRepository.save(product);
		return saveMovement(product, MovementType.ENTRY, quantity, MovementReason.STOCK_ENTRY);
	}

	@Transactional
	public StockMovement registerExit(Long productId, Integer quantity) {
		validateQuantity(quantity);
		Product product = findProduct(productId);
		if (product.getQuantity() < quantity) {
			throw new IllegalArgumentException(
					"Insufficient stock. Available: " + product.getQuantity() + ", requested: " + quantity);
		}
		product.setQuantity(product.getQuantity() - quantity);
		productRepository.save(product);
		return saveMovement(product, MovementType.EXIT, quantity, MovementReason.STOCK_EXIT);
	}

	public StockMovement recordInitialStock(Product product) {
		return saveMovement(product, MovementType.ENTRY, product.getQuantity(), MovementReason.INITIAL_STOCK);
	}

	public List<StockMovement> getMovementsByProduct(Long productId) {
		findProduct(productId);
		return stockMovementRepository.findByProductId(productId);
	}

	public List<StockMovement> getRecentMovements() {
		return stockMovementRepository.findTop10ByOrderByCreatedAtDesc();
	}

	private Product findProduct(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
	}

	private void validateQuantity(Integer quantity) {
		if (quantity == null || quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
	}

	private StockMovement saveMovement(Product product, MovementType type, Integer quantity, MovementReason reason) {
		StockMovement movement = new StockMovement();
		movement.setProduct(product);
		movement.setType(type);
		movement.setQuantity(quantity);
		movement.setReason(reason);
		return stockMovementRepository.save(movement);
	}

}
