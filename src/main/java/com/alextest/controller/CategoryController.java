package com.alextest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alextest.model.Category;
import com.alextest.repository.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/categories")
	public String listCategories(Model model) {
		model.addAttribute("listCategories", categoryRepository.findAll());
		return "categories";
	}
	
	@GetMapping("/categories/new")
	public String showCategoryNewForm(Model model) {
		model.addAttribute("category", new Category());
		return "category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(Category category) {
		categoryRepository.save(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String showEditCategoryForm(@PathVariable Integer id, Model model) {
		model.addAttribute("category", categoryRepository.findById(id).get());
		return "category_form";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}
}
