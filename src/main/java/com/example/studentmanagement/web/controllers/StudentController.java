package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.data.dtos.AddGradeModel;
import com.example.studentmanagement.data.dtos.StudentModel;
import com.example.studentmanagement.services.AcademicRecordService;
import com.example.studentmanagement.services.StudentService;
import com.example.studentmanagement.web.payload.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final AcademicRecordService academicRecordService;
    private final StudentService studentService;

    public StudentController(AcademicRecordService academicRecordService, StudentService studentService) {
        this.academicRecordService = academicRecordService;
        this.studentService = studentService;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/grade/{id}")
    public ResponseEntity<Double> getAverageGrade(@PathVariable Long id) {
        return new ResponseEntity<>(
                academicRecordService.studentAverageGradeFromAllCourses(id),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    @PostMapping("/grade/add")
    public ResponseEntity<ResponseMessage> addGrade(@RequestBody AddGradeModel model) {
        academicRecordService.addStudentGradeToCourse(model.getCourseId(),
                model.getStudentId(), model.getGrade());
        ResponseMessage response = new ResponseMessage();
        response.setMessage("Successfully added grade");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    @GetMapping("/not-enrolled/{courseId}")
    public ResponseEntity<List<StudentModel>> getUnEnrolledStudents(@PathVariable Long courseId) {
        return new ResponseEntity<>(studentService.getUnEnrolledStudents(courseId), HttpStatus.OK);
    }
}
