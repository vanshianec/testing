package com.example.studentmanagement.services.impl;

import com.example.studentmanagement.data.dtos.TeacherModel;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Teacher;
import com.example.studentmanagement.data.repositories.CourseRepository;
import com.example.studentmanagement.data.repositories.TeacherRepository;
import com.example.studentmanagement.exceptions.CourseNotFoundException;
import com.example.studentmanagement.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void add(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public List<TeacherModel> getTeachersNotEnrolledForCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);

        return teacherRepository.findAllByCoursesNotContaining(course)
                .stream()
                .map(t -> new TeacherModel(t.getId(), t.getName()))
                .collect(Collectors.toList());
    }
}
