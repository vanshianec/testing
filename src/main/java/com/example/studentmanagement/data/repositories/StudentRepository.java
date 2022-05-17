package com.example.studentmanagement.data.repositories;

import com.example.studentmanagement.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
}
