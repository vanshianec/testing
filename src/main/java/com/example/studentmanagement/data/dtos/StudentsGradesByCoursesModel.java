package com.example.studentmanagement.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentsGradesByCoursesModel implements Comparable<StudentsGradesByCoursesModel> {

    private String courseName;
    private String studentName;
    private Double studentAverageGradeForCourse;

    @Override
    public String toString() {
        return String.format("Course: %s | Student: %s | Grade: %.2f",
                courseName, studentName, studentAverageGradeForCourse);
    }

    @Override
    public int compareTo(StudentsGradesByCoursesModel other) {
        return Comparator.comparing(StudentsGradesByCoursesModel::getCourseName)
                .thenComparing(StudentsGradesByCoursesModel::getStudentAverageGradeForCourse)
                .compare(this, other);
    }
}
