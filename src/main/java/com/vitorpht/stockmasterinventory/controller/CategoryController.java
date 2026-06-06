package com.vitorpht.stockmasterinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vitorpht.stockmasterinventory.model.Category;
import com.vitorpht.stockmasterinventory.service.CategoryService;
import com.vitorpht.stockmasterinventory.service.ProductService;
import com.vitorpht.stockmasterinventory.service.StockMovementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final ProductService productService;
	private final StockMovementService stockMovementService;

	@GetMapping("/")
	public String dashboard(Model model) {
		model.addAttribute("totalCategories", categoryService.getTotalCategories());
		model.addAttribute("totalProducts", productService.getTotalProducts());
		model.addAttribute("totalStockQuantity", productService.getTotalStockQuantity());
		model.addAttribute("recentMovements", stockMovementService.getRecentMovements());
		return "index";
	}

	@GetMapping("/categories")
	public String listCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "categories";
	}

	@GetMapping("/categories/new")
	public String showCategoryForm(Model model) {
		model.addAttribute("category", new Category());
		return "category-form";
	}

	@PostMapping("/categories")
	public String saveCategory(@Valid @ModelAttribute Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "category-form";
		}
		categoryService.saveCategory(category);
		return "redirect:/categories";
	}

}
