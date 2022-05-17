package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.data.dtos.StudentRegisterModel;
import com.example.studentmanagement.data.dtos.TeacherRegisterModel;
import com.example.studentmanagement.data.dtos.UserLoginModel;
import com.example.studentmanagement.services.security.AuthenticationService;
import com.example.studentmanagement.web.payload.JwtResponse;
import com.example.studentmanagement.web.payload.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginModel loginModel) {
        JwtResponse response = authenticationService.login(loginModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterModel model) {
        authenticationService.registerStudent(model);
        ResponseMessage response = new ResponseMessage();
        response.setMessage(String.format("Student %s registered successfully", model.getName()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherRegisterModel model) {
        authenticationService.registerTeacher(model);
        ResponseMessage response = new ResponseMessage();
        response.setMessage(String.format("Teacher %s registered successfully", model.getName()));
        return ResponseEntity.ok(response);
    }
}