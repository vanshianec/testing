package com.example.studentmanagement.services.impl;

import com.example.studentmanagement.data.dtos.CourseWithAverageGradeViewModel;
import com.example.studentmanagement.data.dtos.CourseWithStudentGradesViewModel;
import com.example.studentmanagement.data.dtos.StudentViewModel;
import com.example.studentmanagement.data.dtos.TeacherPageCourseViewModel;
import com.example.studentmanagement.data.dtos.TeacherViewModel;
import com.example.studentmanagement.data.entities.AcademicRecord;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.entities.Teacher;
import com.example.studentmanagement.data.repositories.AcademicRecordRepository;
import com.example.studentmanagement.data.repositories.CourseRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.data.repositories.TeacherRepository;
import com.example.studentmanagement.exceptions.CourseNotFoundException;
import com.example.studentmanagement.exceptions.RecordAlreadyExistsException;
import com.example.studentmanagement.exceptions.StudentNotFoundException;
import com.example.studentmanagement.exceptions.TeacherNotFoundException;
import com.example.studentmanagement.services.AcademicRecordService;
import com.example.studentmanagement.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private static final String RECORD_ALREADY_EXISTS = "Record with course: %s and student: %s already exists";

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AcademicRecordRepository academicRecordRepository;
    private final AcademicRecordService academicRecordService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository,
                             StudentRepository studentRepository, AcademicRecordRepository academicRecordRepository, AcademicRecordService academicRecordService) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.academicRecordRepository = academicRecordRepository;
        this.academicRecordService = academicRecordService;
    }

    @Override
    public void add(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void removeTeacher(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Teacher teacher = course.getTeacher();
        if (teacher != null) {
            teacher.setCourses(teacher.getCourses()
                    .stream()
                    .filter(c -> !c.getId().equals(course.getId()))
                    .collect(Collectors.toList()));

            course.setTeacher(null);
            courseRepository.save(course);
            teacherRepository.save(teacher);
        }
    }

    @Override
    public void addStudentToCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        if (academicRecordRepository.existsByCourseIdAndStudentId(courseId, studentId)) {
            throw new RecordAlreadyExistsException(
                    String.format(RECORD_ALREADY_EXISTS, course.getName(), student.getName()));
        }

        AcademicRecord academicRecordEntity = new AcademicRecord();
        academicRecordEntity.setCourse(course);
        academicRecordEntity.setStudent(student);
        academicRecordEntity.setGrade(null);
        academicRecordRepository.save(academicRecordEntity);
    }

    @Override
    public void setTeacher(Long courseId, Long teacherId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
        course.setTeacher(teacher);
        teacher.getCourses().add(course);
        courseRepository.save(course);
        teacherRepository.save(teacher);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<TeacherPageCourseViewModel> getTeacherCourses(Long teacherId) {
        List<TeacherPageCourseViewModel> models = new ArrayList<>();
        courseRepository.findAllByTeacherId(teacherId).forEach(course -> {
            TeacherPageCourseViewModel model = new TeacherPageCourseViewModel();
            model.setName(course.getName());
            model.setTotalHours(course.getTotalHours());
            model.setAverageGrade(academicRecordService.courseAverageGrade(course));
            models.add(model);
        });

        return models;
    }

    @Override
    public List<Course> getCoursesByMatchingName(String courseName) {
        return (courseName == null || courseName.isEmpty())
                ? courseRepository.findAll()
                : courseRepository.findAllByNameContains(courseName);
    }

    @Override
    public List<CourseWithStudentGradesViewModel> getCoursesWithStudentsByMatchingStudentName(String studentName) {
        List<CourseWithStudentGradesViewModel> models = new ArrayList<>();
        courseRepository.findAll()
                .forEach(course -> {
                    CourseWithStudentGradesViewModel model = new CourseWithStudentGradesViewModel();
                    model.setId(course.getId());
                    model.setName(course.getName());
                    model.setTeacher(TeacherViewModel.create(course.getTeacher()));
                    model.setTotalHours(course.getTotalHours());
                    Map<Student, List<Double>> studentGrades = new HashMap<>();
                    populateStudentGradesMap(course, studentGrades, studentName);
                    addStudentGradesToModel(model, studentGrades);

                    models.add(model);
                });

        return models;
    }

    private void populateStudentGradesMap(Course course, Map<Student, List<Double>> studentGrades, String studentName) {
        course.getStudentGrades().forEach(record -> {
            Student student = record.getStudent();
            if (student.getName().contains(studentName)) {
                studentGrades.putIfAbsent(student, new ArrayList<>());
                if (record.getGrade() != null) {
                    studentGrades.get(student).add(record.getGrade());
                }
            }
        });
    }

    private void addStudentGradesToModel(CourseWithStudentGradesViewModel model, Map<Student, List<Double>> studentGrades) {
        model.setStudents(studentGrades.entrySet().stream().map(e -> {
            StudentViewModel studentViewModel = new StudentViewModel();
            studentViewModel.setId(e.getKey().getId());
            studentViewModel.setName(e.getKey().getName());
            studentViewModel.setGrades(e.getValue());
            studentViewModel.setAverageGrade(e.getValue().stream()
                    .mapToDouble(Double::doubleValue).average().orElse(0.0));
            if (studentViewModel.getAverageGrade() == 0.0) {
                studentViewModel.setAverageGrade(null);
            }
            return studentViewModel;
        }).collect(Collectors.toList()));
    }
}
