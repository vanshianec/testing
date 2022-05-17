package com.example.studentmanagement.web.payload;

import com.example.studentmanagement.data.dtos.UserRolesModel;
import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UserRolesModel user;

    public JwtResponse(String token, UserRolesModel user) {
        this.token = token;
        this.user = user;
    }
}
