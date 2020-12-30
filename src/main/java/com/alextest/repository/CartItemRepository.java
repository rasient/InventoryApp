package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
