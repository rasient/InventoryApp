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
import com.alextest.model.CartItem;
import com.alextest.model.User;
import com.alextest.repository.CartItemRepository;
import com.alextest.repository.ProductRepository;
import com.alextest.repository.UserRepository;

@Controller
public class CartItemController {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/cartItems")
	public String listProducts(Model model) {
		return listByPage(model, 1, "id", "asc", "");
	}
	
	@GetMapping("/cartItems/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber, @RequestParam String sortField, @RequestParam String sortDir, @RequestParam String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Page<CartItem> page = cartItemRepository.findAll(keyword, PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort));
		
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listCartItems", page.getContent());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		
		return "cart_items";
	}
	
	@GetMapping("/cartItems/new")
	public String showProductNewForm(Model model) {
		model.addAttribute("cartItem", new CartItem());
		model.addAttribute("listProducts", productRepository.findAll());
		model.addAttribute("listUsers", userRepository.findAll());
		return "cart_item_form";
	}
	
	@PostMapping("/cartItems/save")
	public String saveCartItems(CartItem cartItem) {
		cartItemRepository.save(cartItem);
		return "redirect:/cartItems";
	}
	
	@GetMapping("/cartItems/edit/{id}")
	public String showProductEditForm(@PathVariable Integer id, Model model) {
		model.addAttribute("cartItem", cartItemRepository.findById(id).get());
		model.addAttribute("listProducts", productRepository.findAll());
		model.addAttribute("listUsers", userRepository.findAll());
		return "cart_item_form";
	}
	
	@GetMapping("/cartItems/delete/{id}")
	public String deleteProduct(@PathVariable Integer id, Model model) {
		cartItemRepository.deleteById(id);
		return "redirect:/cartItems";
	}
}
