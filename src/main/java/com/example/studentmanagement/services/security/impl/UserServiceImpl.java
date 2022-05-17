package com.example.studentmanagement.services.security.impl;

import com.example.studentmanagement.data.dtos.UserAdminPageModel;
import com.example.studentmanagement.data.entities.Role;
import com.example.studentmanagement.data.entities.RoleType;
import com.example.studentmanagement.data.entities.User;
import com.example.studentmanagement.data.repositories.RoleRepository;
import com.example.studentmanagement.data.repositories.UserRepository;
import com.example.studentmanagement.exceptions.UserNotFoundException;
import com.example.studentmanagement.services.security.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserAdminPageModel> getNonAdminUsers() {
        Role adminRole = roleRepository.findByName("ADMIN");

        return userRepository.findAllByRolesNotContaining(adminRole)
                .stream()
                .map(u -> {
                    UserAdminPageModel model = new UserAdminPageModel();
                    model.setId(u.getId());
                    model.setUsername(u.getUsername());
                    model.setRoles(u.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
                    model.setUserType(u.getStudent() != null ? "student" : "teacher");
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void setRoles(Long userId, List<RoleType> roles) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setRoles(createRoles(roles));
        userRepository.save(user);
    }

    private Set<Role> createRoles(List<RoleType> roleTypes) {
        Set<Role> roles = new HashSet<>();

        for (RoleType roleType : roleTypes) {
            Role role = roleRepository.findByName(roleType.name());
            if (role == null) {
                role = roleRepository.save(new Role(roleType));
            }

            roles.add(role);
        }

        return roles;
    }
}
