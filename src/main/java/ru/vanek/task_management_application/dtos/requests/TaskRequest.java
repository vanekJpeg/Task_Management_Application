package ru.vanek.task_management_application.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequest {
    @Size(min = 3,max = 20, message = "заголовок должен быть от 3 до 20 символов")
    private String header;
    @Size(min = 3,max = 500, message = "заголовок должен быть от 3 до 500 символов")
    private String description;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email не может быть пустым")
    private String executorEmail;
}
