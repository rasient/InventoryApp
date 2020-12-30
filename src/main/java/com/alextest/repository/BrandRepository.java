package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{

}
