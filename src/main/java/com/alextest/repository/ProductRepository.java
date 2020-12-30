package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
