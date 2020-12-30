package com.alextest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alextest.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
}
