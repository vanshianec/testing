package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherPageCourseViewModel {
    private String name;
    private int totalHours;
    private Double averageGrade;
}
