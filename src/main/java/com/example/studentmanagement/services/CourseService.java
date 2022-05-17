package com.example.studentmanagement.services;

import com.example.studentmanagement.data.dtos.CourseWithAverageGradeViewModel;
import com.example.studentmanagement.data.dtos.CourseWithStudentGradesViewModel;
import com.example.studentmanagement.data.dtos.TeacherPageCourseViewModel;
import com.example.studentmanagement.data.entities.Course;

import java.util.List;

public interface CourseService {
    void add(Course course);

    void removeTeacher(Long courseId);

    void addStudentToCourse(Long courseId, Long studentId);

    void setTeacher(Long courseId, Long teacherId);

    List<Course> getAllCourses();

    List<TeacherPageCourseViewModel> getTeacherCourses(Long teacherId);

    List<Course> getCoursesByMatchingName(String courseName);

    List<CourseWithStudentGradesViewModel> getCoursesWithStudentsByMatchingStudentName(String studentName);
}
