package com.example.studentmanagement.data.repositories;

import com.example.studentmanagement.data.entities.AcademicRecord;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcademicRecordRepository extends JpaRepository<AcademicRecord, Long> {
    List<AcademicRecord> findAllByGradeIsNotNull();

    List<AcademicRecord> findAllByCourseAndGradeIsNotNull(Course course);

    List<AcademicRecord> findAllByStudentAndGradeIsNotNull(Student student);

    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);

    Optional<AcademicRecord> findByCourseAndStudent(Course course, Student student);

    List<AcademicRecord> findAllByCourseIdNotLikeOrCourseIsNull(Long courseId);
}
