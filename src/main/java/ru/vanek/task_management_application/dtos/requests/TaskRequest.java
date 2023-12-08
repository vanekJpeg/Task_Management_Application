package ru.vanek.task_management_application.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequest {
    private String header;

    private String description;

    private String executorEmail;
}
