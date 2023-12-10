package ru.vanek.task_management_application.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    @Size(min = 3,max = 20, message = "имя должно быть от 3 до 20 символов ")
    private String username;
    @Pattern(regexp = "^.*(?=.{5,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&?_ \"]).*$",message = "Пароль должен быть от 5 символов, содержать букву нижнего или вехнего регистра, а также один из специальных символов: !#$%&?_ ")
    private String password;
    public String confirmPassword;
    @Email(message = "Email is not valid, regexp = ^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;

    public UserRequest() {
    }
}
