package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CourseViewModel {
    private Long id;
    private String name;
    private TeacherViewModel teacher;
    private int totalHours;
}
