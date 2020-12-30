package com.alextest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alextest.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
