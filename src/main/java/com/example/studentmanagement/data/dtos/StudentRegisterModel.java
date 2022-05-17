package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StudentRegisterModel extends UserRegisterModel{
    private int age;
}
