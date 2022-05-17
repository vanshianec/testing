package com.example.studentmanagement.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentViewModel {
    private Long id;
    private String name;
    private List<Double> grades;
    private Double averageGrade;
}
