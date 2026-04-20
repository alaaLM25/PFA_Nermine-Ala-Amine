package com.example.ecomback.repository;

import com.example.ecomback.entity.Role;
import com.example.ecomback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long countByRole(Role role);
}
