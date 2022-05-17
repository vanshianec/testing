package com.example.studentmanagement.services;

import com.example.studentmanagement.data.dtos.StudentsGradesByCoursesModel;
import com.example.studentmanagement.data.entities.Course;

import java.util.List;

public interface AcademicRecordService {
    void addStudentGradeToCourse(Long courseId, Long studentId, double grade);

    Double courseAverageGrade(Course course);

    Double studentAverageGradeFromAllCourses(Long studentId);

    List<StudentsGradesByCoursesModel> getStudentsGroupedByCourseInAlphabeticOrderSortedByGrades();
}
