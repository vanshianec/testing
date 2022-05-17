package com.example.studentmanagement.services;

import com.example.studentmanagement.data.entities.AcademicRecord;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.repositories.AcademicRecordRepository;
import com.example.studentmanagement.data.repositories.CourseRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.services.impl.AcademicRecordServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcademicRecordServiceTest {

    private AcademicRecordService recordService;

    @Mock
    private AcademicRecordRepository recordRepositoryMock;
    @Mock
    private CourseRepository courseRepositoryMock;
    @Mock
    private StudentRepository studentRepositoryMock;

    private static final Course testCourse = new Course();
    private static final Student testStudent = new Student();
    private static final AcademicRecord record = new AcademicRecord();

    @BeforeClass
    public static void setConstantsValues() {
        testCourse.setId(999L);
        testStudent.setId(9999L);
        record.setId(7L);
    }

    @Before
    public void init() {
        recordService = new AcademicRecordServiceImpl(recordRepositoryMock,
                courseRepositoryMock,
                studentRepositoryMock);
    }

    @Test
    public void addStudentGradeForCourseShouldStoreTheGradeInTheRepositories() {
        when(courseRepositoryMock.findById(testCourse.getId())).thenReturn(Optional.of(testCourse));
        when(studentRepositoryMock.findById(testStudent.getId())).thenReturn(Optional.of(testStudent));
        AcademicRecord record = new AcademicRecord();
        when(recordRepositoryMock.findByCourseAndStudent(testCourse, testStudent)).thenReturn(Optional.of(record));
        recordService.addStudentGradeToCourse(testCourse.getId(), testStudent.getId(), 4.0);
        verify(recordRepositoryMock).save(record);
    }
}
