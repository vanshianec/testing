package com.example.studentmanagement.services.impl;

import com.example.studentmanagement.data.dtos.StudentModel;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.repositories.AcademicRecordRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final AcademicRecordRepository academicRecordRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, AcademicRecordRepository academicRecordRepository) {
        this.studentRepository = studentRepository;
        this.academicRecordRepository = academicRecordRepository;
    }

    @Override
    public void add(Student student) {
        studentRepository.save(student);
    }

    @Override
    public List<StudentModel> getUnEnrolledStudents(Long courseId) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getUser().getRoles().stream().anyMatch(r -> r.getName().equals("STUDENT"))
                        && (s.getCourseGrades().isEmpty()
                        || !academicRecordRepository.existsByCourseIdAndStudentId(courseId, s.getId())))
                .map(s -> {
                    StudentModel model = new StudentModel();
                    model.setId(s.getId());
                    model.setName(s.getName());
                    return model;
                })
                .collect(Collectors.toList());
    }
}
