package com.example.studentmanagement.data.dtos;

import com.example.studentmanagement.data.entities.Degree;
import com.example.studentmanagement.data.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherViewModel {
    private String name;
    private Degree degree;

    public static TeacherViewModel create(Teacher teacher) {
        if (teacher == null) {
            return new TeacherViewModel();
        }

        return new TeacherViewModel(teacher.getName(), teacher.getDegree());
    }
}
