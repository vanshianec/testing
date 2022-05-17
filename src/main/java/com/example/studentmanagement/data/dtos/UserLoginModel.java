package com.example.studentmanagement.data.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginModel {
    private String username;
    private String password;
}
