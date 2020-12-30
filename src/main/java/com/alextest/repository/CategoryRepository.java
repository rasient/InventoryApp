package com.alextest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alextest.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query("SELECT c FROM Category c WHERE CONCAT(c.id, ' ', c.name) LIKE %?1%")
	public Page<Category> findAll(String keyword, Pageable pageable);
}
