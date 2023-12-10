package ru.vanek.task_management_application.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class  JwtRequest {
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
    @NotEmpty
    private String password;

    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
