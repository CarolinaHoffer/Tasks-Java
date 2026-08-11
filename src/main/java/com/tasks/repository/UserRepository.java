package com.tasks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasks.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	Optional<User> findByEmail(String email);

	Optional<User> findByEmailAndIdNot(String email, Long id);
}
