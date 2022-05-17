package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.data.dtos.CourseWithAverageGradeViewModel;
import com.example.studentmanagement.data.dtos.CourseWithStudentGradesViewModel;
import com.example.studentmanagement.data.dtos.TeacherPageCourseViewModel;
import com.example.studentmanagement.data.dtos.CourseViewModel;
import com.example.studentmanagement.data.dtos.TeacherViewModel;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.services.AcademicRecordService;
import com.example.studentmanagement.services.CourseService;
import com.example.studentmanagement.web.payload.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final AcademicRecordService academicRecordService;

    public CourseController(CourseService courseService, AcademicRecordService academicRecordService) {
        this.courseService = courseService;
        this.academicRecordService = academicRecordService;
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    @GetMapping("/students")
    public ResponseEntity<List<CourseWithStudentGradesViewModel>> getCoursesWithStudents(@RequestParam String searchWord) {
        return new ResponseEntity<>(courseService.getCoursesWithStudentsByMatchingStudentName(searchWord),
                HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER')")
    @GetMapping("/all")
    public ResponseEntity<List<CourseViewModel>> getCourses(@RequestParam String searchWord) {
        if (searchWord == null) {
            searchWord = "";
        }

        List<CourseViewModel> courseViewModels = courseService.getCoursesByMatchingName(searchWord)
                .stream()
                .map(c -> {
                    CourseViewModel model = new CourseViewModel();
                    model.setName(c.getName());
                    model.setTeacher(TeacherViewModel.create(c.getTeacher()));
                    model.setTotalHours(c.getTotalHours());
                    return model;
                }).collect(Collectors.toList());

        return new ResponseEntity<>(courseViewModels, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<TeacherPageCourseViewModel>> getTeacherCourses(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getTeacherCourses(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/average-grades")
    public ResponseEntity<List<CourseWithAverageGradeViewModel>> getCoursesWithAverageGrade(@RequestParam String searchWord) {
        if (searchWord == null) {
            searchWord = "";
        }

        List<Course> courses = courseService.getCoursesByMatchingName(searchWord);
        List<CourseWithAverageGradeViewModel> models = new ArrayList<>();
        courses.forEach(course -> {
            CourseWithAverageGradeViewModel model = new CourseWithAverageGradeViewModel();
            model.setName(course.getName());
            model.setTotalHours(course.getTotalHours());
            model.setTeacher(TeacherViewModel.create(course.getTeacher()));
            model.setAverageGrade(academicRecordService.courseAverageGrade(course));
            models.add(model);
        });

        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    @PostMapping("/add-student")
    public ResponseEntity<ResponseMessage> addStudentToCourse(@RequestParam(value = "courseId") Long courseId,
                                                              @RequestParam(value = "studentId") Long studentId) {
        this.courseService.addStudentToCourse(courseId, studentId);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Successfully added student to course");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/set-teacher")
    public ResponseEntity<ResponseMessage> setCourseTeacher(@RequestParam(value = "courseId") Long courseId,
                                                            @RequestParam(value = "teacherId") Long teacherId) {
        this.courseService.setTeacher(courseId, teacherId);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Successfully added student to course");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/remove-teacher/{courseId}")
    public ResponseEntity<ResponseMessage> removeCourseTeacher(@PathVariable Long courseId) {
        this.courseService.removeTeacher(courseId);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Successfully removed teacher from course");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
