package com.example.studentmanagement.services;

import com.example.studentmanagement.data.entities.Teacher;
import com.example.studentmanagement.data.repositories.TeacherRepository;
import com.example.studentmanagement.services.impl.TeacherServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepositoryMock;

    private static final Teacher testTeacher = new Teacher();

    @BeforeClass
    public static void setConstantsValues() {
        testTeacher.setId(88L);
    }

    @Before
    public void init() {
        teacherService = new TeacherServiceImpl(teacherRepositoryMock, courseRepository);
    }

    @Test
    public void addShouldInsertTheTeacherInTheRepository() {
        teacherService.add(testTeacher);
        verify(teacherRepositoryMock).save(testTeacher);
    }

}
