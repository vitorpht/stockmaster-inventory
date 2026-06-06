package com.vitorpht.stockmasterinventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorpht.stockmasterinventory.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

	List<StockMovement> findByProductId(Long productId);

	List<StockMovement> findTop10ByOrderByCreatedAtDesc();

}
