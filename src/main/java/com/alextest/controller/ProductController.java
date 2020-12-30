package com.alextest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alextest.model.Product;
import com.alextest.repository.CategoryRepository;
import com.alextest.repository.ProductRepository;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
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
	
	@GetMapping("/products")
	public String listProducts(Model model) {
		model.addAttribute("listProducts", productRepository.findAll());
		return "products";
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
