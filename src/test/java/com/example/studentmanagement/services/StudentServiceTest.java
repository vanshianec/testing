package com.example.studentmanagement.services;

import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.services.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepositoryMock;

    private static final Student testStudent = new Student();

    @BeforeClass
    public static void setConstantsValues() {
        testStudent.setId(9999L);
    }

    @Before
    public void init() {
        studentService = new StudentServiceImpl(studentRepositoryMock, academicRecordRepository);
    }

    @Test
    public void addShouldInsertTheStudentInTheRepository() {
        studentService.add(testStudent);
        verify(studentRepositoryMock).save(testStudent);
    }
}
