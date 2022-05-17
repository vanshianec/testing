package com.example.studentmanagement.data.repositories;

import com.example.studentmanagement.data.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTeacherId(Long id);

    List<Course> findAllByNameContains(String searchWord);
}
