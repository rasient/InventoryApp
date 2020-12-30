package com.alextest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alextest.Constants;
import com.alextest.model.Category;
import com.alextest.repository.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/categories")
	public String listCategories(Model model) {
		return listByPage(model, 1, "id", "asc", "");
	}
	
	@GetMapping("/categories/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber, @RequestParam String sortField, @RequestParam String sortDir, @RequestParam String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Page<Category> page = categoryRepository.findAll(keyword, PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort));
		
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listCategories", page.getContent());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		
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
