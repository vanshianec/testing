package com.example.studentmanagement.services;

import com.example.studentmanagement.data.entities.AcademicRecord;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.entities.Teacher;
import com.example.studentmanagement.data.repositories.AcademicRecordRepository;
import com.example.studentmanagement.data.repositories.CourseRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.data.repositories.TeacherRepository;
import com.example.studentmanagement.services.impl.CourseServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    private CourseService courseService;

    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private StudentRepository studentRepositoryMock;
    @Mock
    private TeacherRepository teacherRepositoryMock;
    @Mock
    private AcademicRecordRepository recordRepositoryMock;

    private static final Course testCourse = new Course();
    private static final Course testCourse2 = new Course();
    private static final Teacher testTeacher = new Teacher();
    private static final Student testStudent = new Student();

    @BeforeClass
    public static void setConstantsValues() {
        testCourse.setId(999L);
        testCourse2.setId(9L);
        testTeacher.setId(88L);
        testStudent.setId(9999L);
    }

    @Before
    public void init() {
        courseService = new CourseServiceImpl(courseRepositoryMock,
                teacherRepositoryMock,
                studentRepositoryMock,
                recordRepositoryMock, academicRecordService);
    }

    @Test
    public void addShouldInsertTheCourseInTheRepository() {
        courseService.add(testCourse);
        verify(courseRepositoryMock).save(testCourse);
    }

    @Test
    public void getAllCoursesShouldReturnTheCoursesInTheRepository() {
        List<Course> courses = new ArrayList<>() {{
            add(testCourse);
            add(testCourse2);
        }};
        when(courseRepositoryMock.findAll()).thenReturn(courses);
        assertEquals(courseService.getAllCourses(), courses);
    }

    @Test
    public void setTeacherShouldAddTheTeacherInTheRepository() {
        when(courseRepositoryMock.findById(testCourse.getId())).thenReturn(Optional.of(testCourse));
        when(teacherRepositoryMock.findById(testTeacher.getId())).thenReturn(Optional.of(testTeacher));
        courseService.setTeacher(testCourse.getId(), testTeacher.getId());
        assertEquals(testCourse.getTeacher(), testTeacher);
        assertTrue(testTeacher.getCourses().contains(testCourse));
        verify(courseRepositoryMock).save(testCourse);
        verify(teacherRepositoryMock).save(testTeacher);
    }

    @Test
    public void testAddStudentToCourseShouldUpdateTheRepositories() {
        when(courseRepositoryMock.findById(testCourse.getId())).thenReturn(Optional.of(testCourse));
        when(studentRepositoryMock.findById(testStudent.getId())).thenReturn(Optional.of(testStudent));
        courseService.addStudentToCourse(testCourse.getId(), testStudent.getId());
        AcademicRecord academicRecordEntity = new AcademicRecord();
        academicRecordEntity.setCourse(testCourse);
        academicRecordEntity.setStudent(testStudent);
        academicRecordEntity.setGrades(null);
        verify(recordRepositoryMock).save(academicRecordEntity);
    }
}
