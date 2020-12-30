package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
}
