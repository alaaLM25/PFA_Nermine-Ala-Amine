package com.example.ecomback.service;

import com.example.ecomback.dto.UserDTO;
import com.example.ecomback.entity.Role;
import com.example.ecomback.entity.User;
import com.example.ecomback.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.fromEntity(user);
    }

    public UserDTO updateUserRole(Long id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            Role role = Role.valueOf(roleName);
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: '" + roleName + "'. Allowed: ROLE_CLIENT, ROLE_ADMIN");
        }
        userRepository.save(user);
        return UserDTO.fromEntity(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public long countByRole(Role role) {
        return userRepository.countByRole(role);
    }
}
