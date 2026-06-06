package com.vitorpht.stockmasterinventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitorpht.stockmasterinventory.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
