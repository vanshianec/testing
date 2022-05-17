package com.example.studentmanagement.data.repositories;

import com.example.studentmanagement.data.entities.Role;
import com.example.studentmanagement.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findAllByRolesNotContaining(Role role);
}
