package com.example.studentmanagement.services;

import com.example.studentmanagement.data.dtos.StudentModel;
import com.example.studentmanagement.data.entities.Student;

import java.util.List;

public interface StudentService {
    void add(Student student);

    List<StudentModel> getUnEnrolledStudents(Long courseId);
}
