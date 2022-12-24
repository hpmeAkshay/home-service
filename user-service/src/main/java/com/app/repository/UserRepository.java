package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

	public Optional<Users> findByEmail(String email);
	@Query("SELECT u FROM Users u WHERE u.email = :email")
	public Users getUserByUsername(@Param("email") String username);
}
