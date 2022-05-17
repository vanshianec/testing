package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseWithStudentGradesViewModel extends CourseViewModel{
    private List<StudentViewModel> students;

    public CourseWithStudentGradesViewModel(){
        this.students = new ArrayList<>();
    }
}
