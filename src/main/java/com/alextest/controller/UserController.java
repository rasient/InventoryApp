package com.alextest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alextest.model.User;
import com.alextest.repository.RoleRepository;
import com.alextest.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/users")
	public String showUserList(Model model) {
		model.addAttribute("listUsers", userRepository.findAll());
		return "users";
	}
	
	@GetMapping("/users/new")
	public String showCreateNewUserForm(Model model) {
		model.addAttribute("listRoles", roleRepository.findAll());
		model.addAttribute("user", new User());
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String showCreateNewUserForm(@PathVariable Integer id, Model model) {
		model.addAttribute("listRoles", roleRepository.findAll());
		model.addAttribute("user", userRepository.findById(id).get());
		return "user_form";
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
		return "redirect:/users";
	}
}
