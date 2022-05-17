package com.example.studentmanagement.data.repositories;

import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByCoursesNotContaining(Course course);
}
