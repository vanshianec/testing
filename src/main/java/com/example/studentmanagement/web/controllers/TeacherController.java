package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.data.dtos.StudentModel;
import com.example.studentmanagement.data.dtos.TeacherModel;
import com.example.studentmanagement.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    @GetMapping("/not-enrolled/{courseId}")
    public ResponseEntity<List<TeacherModel>> getUnEnrolledStudents(@PathVariable Long courseId) {
        return new ResponseEntity<>(teacherService.getTeachersNotEnrolledForCourse(courseId), HttpStatus.OK);
    }
}
