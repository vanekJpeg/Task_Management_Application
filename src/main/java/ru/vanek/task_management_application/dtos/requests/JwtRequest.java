package ru.vanek.task_management_application.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class  JwtRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
