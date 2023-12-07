package ru.vanek.task_management_application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    public String confirmPassword;
    private String email;
}
