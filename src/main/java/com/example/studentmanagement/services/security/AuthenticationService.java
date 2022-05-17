package com.example.studentmanagement.services.security;

import com.example.studentmanagement.data.dtos.StudentRegisterModel;
import com.example.studentmanagement.data.dtos.TeacherRegisterModel;
import com.example.studentmanagement.data.dtos.UserLoginModel;
import com.example.studentmanagement.web.payload.JwtResponse;

public interface AuthenticationService {
    JwtResponse login(UserLoginModel model);

    void registerStudent(StudentRegisterModel model);

    void registerTeacher(TeacherRegisterModel model);
}
