package com.example.studentmanagement.services.security;

import com.example.studentmanagement.data.dtos.UserAdminPageModel;
import com.example.studentmanagement.data.entities.RoleType;

import java.util.List;

public interface UserService {
    void setRoles(Long userId, List<RoleType> roles);

    List<UserAdminPageModel> getNonAdminUsers();
}
