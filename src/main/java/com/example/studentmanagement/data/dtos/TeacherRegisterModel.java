package com.example.studentmanagement.data.dtos;

import com.example.studentmanagement.data.entities.Degree;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeacherRegisterModel extends UserRegisterModel {
    private Degree degree;
}
