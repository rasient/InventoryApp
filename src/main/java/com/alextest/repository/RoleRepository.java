package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
