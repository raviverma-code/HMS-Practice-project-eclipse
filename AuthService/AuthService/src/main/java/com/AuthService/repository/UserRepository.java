package com.AuthService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AuthService.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{

	 boolean existsByUsername(String username);
	 boolean existsByEmail(String email);
	 User   findByUsername(String username);
}
