package com.alextest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alextest.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

	@Query("SELECT c FROM CartItem c WHERE CONCAT(c.id, ' ', c.product.name, ' ', c.user.email, ' ', c.quantity) LIKE %?1%")
	public Page<CartItem> findAll(String keyword, Pageable pageable);
	
}
