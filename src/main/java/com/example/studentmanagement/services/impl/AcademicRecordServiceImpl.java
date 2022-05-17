package com.example.studentmanagement.services.impl;

import com.example.studentmanagement.data.dtos.StudentsGradesByCoursesModel;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.AcademicRecord;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.repositories.CourseRepository;
import com.example.studentmanagement.data.repositories.AcademicRecordRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.exceptions.CourseNotFoundException;
import com.example.studentmanagement.exceptions.InvalidGradeException;
import com.example.studentmanagement.exceptions.StudentNotFoundException;
import com.example.studentmanagement.services.AcademicRecordService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class AcademicRecordServiceImpl implements AcademicRecordService {
    private static final double MIN_GRADE = 2.0;
    private static final double MAX_GRADE = 6.0;
    private static final String INVALID_GRADE_MESSAGE = "Grade should be between %.2f and %.2f";
    private static final String STUDENT_NOT_ENROLLED_FOR_COURSE =
            "Student: %s is not enrolled for course: %s";

    private final AcademicRecordRepository academicRecordRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AcademicRecordServiceImpl(AcademicRecordRepository academicRecordRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.academicRecordRepository = academicRecordRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudentGradeToCourse(Long courseId, Long studentId, double grade) {
        if (grade < MIN_GRADE || grade > MAX_GRADE) {
            throw new InvalidGradeException(String.format(INVALID_GRADE_MESSAGE, MIN_GRADE, MAX_GRADE));
        }

        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        AcademicRecord academicRecord = new AcademicRecord();
        academicRecord.setCourse(course);
        academicRecord.setStudent(student);
        academicRecord.setGrade(grade);
        academicRecordRepository.save(academicRecord);
    }

    @Override
    public Double courseAverageGrade(Course course) {
        Map<Student, List<Double>> studentGrades = new HashMap<>();

        academicRecordRepository.findAllByCourseAndGradeIsNotNull(course)
                .forEach(record -> {
                    studentGrades.putIfAbsent(record.getStudent(), new ArrayList<>());
                    studentGrades.get(record.getStudent()).add(record.getGrade());
                });

        return getTotalAverageFromStudentGrades(studentGrades);
    }

    @Nullable
    private Double getTotalAverageFromStudentGrades(Map<Student, List<Double>> studentGrades) {
        double averageGrade = studentGrades.values()
                .stream()
                .mapToDouble(e -> e.stream().mapToDouble(d -> d).average().orElse(0.0))
                .average()
                .orElse(0.0);

        return averageGrade == 0.0 ? null : averageGrade;
    }

    @Override
    public Double studentAverageGradeFromAllCourses(Long studentId) {
        //TODO not 100% valid average, refactor like totalAverageOfStudentGrades
        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        double grade = academicRecordRepository.findAllByStudentAndGradeIsNotNull(student)
                .stream()
                .mapToDouble(AcademicRecord::getGrade)
                .average()
                .orElse(0.0);

        return grade == 0.0 ? null : grade;
    }

    @Override
    public List<StudentsGradesByCoursesModel> getStudentsGroupedByCourseInAlphabeticOrderSortedByGrades() {
        return academicRecordRepository.findAllByGradeIsNotNull()
                .stream()
                .map(record -> new StudentsGradesByCoursesModel(
                        record.getCourse().getName(),
                        record.getStudent().getName(),
                        record.getGrade()))
                .sorted()
                .collect(Collectors.toList());
    }
}
