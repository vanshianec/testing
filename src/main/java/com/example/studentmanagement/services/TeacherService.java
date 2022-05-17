package com.example.studentmanagement.services;

import com.example.studentmanagement.data.dtos.TeacherModel;
import com.example.studentmanagement.data.dtos.TeacherViewModel;
import com.example.studentmanagement.data.entities.Teacher;

import java.util.List;

public interface TeacherService {
    void add(Teacher teacher);

    List<TeacherModel> getTeachersNotEnrolledForCourse(Long courseId);
}
