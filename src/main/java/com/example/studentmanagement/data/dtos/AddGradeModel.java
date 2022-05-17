package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddGradeModel {
    private Long studentId;
    private Long courseId;
    private Double grade;
}
