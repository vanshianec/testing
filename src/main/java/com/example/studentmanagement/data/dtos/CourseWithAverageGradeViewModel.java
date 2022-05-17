package com.example.studentmanagement.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseWithAverageGradeViewModel extends CourseViewModel {
    private Double averageGrade;
}
