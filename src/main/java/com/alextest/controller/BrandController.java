package com.alextest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alextest.model.Brand;
import com.alextest.repository.BrandRepository;
import com.alextest.repository.CategoryRepository;

@Controller
public class BrandController {

	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/brands/new")
	public String showBrandNewForm(Model model) {
		model.addAttribute("brand", new Brand());
		model.addAttribute("listCategories", categoryRepository.findAll());
		return "brand_form";
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand) {
		brandRepository.save(brand);
		return "redirect:/brands";
	}
	
	@GetMapping("/brands")
	public String listBrands(Model model) {
		model.addAttribute("listBrands", brandRepository.findAll());
		return "brands";
	}
	
	@GetMapping("/brands/edit/{id}")
	public String showBrandEditForm(@PathVariable Integer id, Model model) {
		model.addAttribute("brand", brandRepository.findById(id).get());
		model.addAttribute("listCategories", categoryRepository.findAll());
		return "brand_form";
	}
	
	@GetMapping("/brands/delete/{id}")
	public String deleteBrand(@PathVariable Integer id) {
		brandRepository.deleteById(id);
		return "redirect:/brands";
	}
}
