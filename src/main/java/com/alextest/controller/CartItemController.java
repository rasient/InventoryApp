package com.alextest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		model.addAttribute("listCartItems", cartItemRepository.findAll());
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
