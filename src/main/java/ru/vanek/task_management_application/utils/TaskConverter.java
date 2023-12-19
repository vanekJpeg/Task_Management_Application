package ru.vanek.task_management_application.utils;

import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Task;

public interface TaskConverter {
    public TaskResponse convertToResponse(Task task);
}
