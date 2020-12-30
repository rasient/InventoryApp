package com.alextest.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.alextest.model.Product;
import com.alextest.repository.CategoryRepository;
import com.alextest.repository.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/products")
	public String listProducts(Model model) {
		return listByPage(model, 1, "id", "asc", "");
	}
	
	@GetMapping("/products/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber, @RequestParam String sortField, @RequestParam String sortDir, @RequestParam String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Page<Product> page = productRepository.findAll(keyword, PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort));
		
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listProducts", page.getContent());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		
		return "products";
	}
	
	@GetMapping("/products/new")
	public String showProductNewForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("listCategories", categoryRepository.findAll());
		return "product_form";
	}
	
	@PostMapping("products/save")
	public String saveProduct(Product product, HttpServletRequest request) {
		String[] detailIds = request.getParameterValues("detailId");
		String[] detailNames = request.getParameterValues("detailName");
		String[] detailValues = request.getParameterValues("detailValue");
		
		for (int i = 0; i <detailNames.length; i++) {
			if (detailIds != null && detailIds.length > 0) {
				product.addDetail(Integer.valueOf(detailIds[i]), detailNames[i], detailValues[i]);
			} else {
				product.addDetail(detailNames[i], detailValues[i]);
			}
		}
		
		productRepository.save(product);
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String showProductEditForm(@PathVariable Integer id, Model model) {
		model.addAttribute("product", productRepository.findById(id).get());
		model.addAttribute("listCategories", categoryRepository.findAll());
		return "product_form";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable Integer id, Model model) {
		productRepository.deleteById(id);
		return "redirect:/products";
	}
}
