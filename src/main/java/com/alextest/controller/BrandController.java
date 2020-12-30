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
import com.alextest.model.Brand;
import com.alextest.repository.BrandRepository;
import com.alextest.repository.CategoryRepository;

@Controller
public class BrandController {

	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/brands")
	public String listProducts(Model model) {
		return listByPage(model, 1, "id", "asc", "");
	}
	
	@GetMapping("/brands/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber, @RequestParam String sortField, @RequestParam String sortDir, @RequestParam String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Page<Brand> page = brandRepository.findAll(keyword, PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort));
		
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listBrands", page.getContent());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		
		return "brands";
	}
	
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
