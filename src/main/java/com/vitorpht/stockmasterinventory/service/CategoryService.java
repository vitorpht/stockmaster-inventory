package com.vitorpht.stockmasterinventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitorpht.stockmasterinventory.model.Category;
import com.vitorpht.stockmasterinventory.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
	}

	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	public long getTotalCategories() {
		return categoryRepository.count();
	}

}
