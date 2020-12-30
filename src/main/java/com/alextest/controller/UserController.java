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
		return listByPage(model, 1, "id", "asc", "");
	}
	
	@GetMapping("/users/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber, @RequestParam String sortField, @RequestParam String sortDir, @RequestParam String keyword){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Page<User> page = userRepository.findAll(keyword, PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort));
		
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("listUsers", page.getContent());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		
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
