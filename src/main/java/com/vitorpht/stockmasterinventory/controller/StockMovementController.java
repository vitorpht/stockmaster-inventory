package com.vitorpht.stockmasterinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vitorpht.stockmasterinventory.service.ProductService;
import com.vitorpht.stockmasterinventory.service.StockMovementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StockMovementController {

	private final StockMovementService stockMovementService;
	private final ProductService productService;

	@GetMapping("/stock")
	public String stockManagement(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "stock";
	}

	@GetMapping("/stock/entry/{productId}")
	public String showEntryForm(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "stock-entry";
	}

	@PostMapping("/stock/entry/{productId}")
	public String registerEntry(@PathVariable Long productId, @RequestParam(required = false) Integer quantity,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			stockMovementService.registerEntry(productId, quantity);
			redirectAttributes.addFlashAttribute("successMessage", "Stock entry registered successfully.");
			return "redirect:/stock";
		} catch (IllegalArgumentException e) {
			model.addAttribute("product", productService.getProductById(productId));
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("quantity", quantity);
			return "stock-entry";
		}
	}

	@GetMapping("/stock/exit/{productId}")
	public String showExitForm(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		return "stock-exit";
	}

	@PostMapping("/stock/exit/{productId}")
	public String registerExit(@PathVariable Long productId, @RequestParam(required = false) Integer quantity,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			stockMovementService.registerExit(productId, quantity);
			redirectAttributes.addFlashAttribute("successMessage", "Stock exit registered successfully.");
			return "redirect:/stock";
		} catch (IllegalArgumentException e) {
			model.addAttribute("product", productService.getProductById(productId));
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute("quantity", quantity);
			return "stock-exit";
		}
	}

	@GetMapping("/stock/history/{productId}")
	public String showHistory(@PathVariable Long productId, Model model) {
		model.addAttribute("product", productService.getProductById(productId));
		model.addAttribute("movements", stockMovementService.getMovementsByProduct(productId));
		return "stock-history";
	}

}
