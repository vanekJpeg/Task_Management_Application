package ru.vanek.task_management_application.utils.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.Status;
import ru.vanek.task_management_application.utils.TaskConverter;
import ru.vanek.task_management_application.utils.UserConverter;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TaskConverterImpl implements TaskConverter {
    private final CommentConverter commentConverter;
    public TaskConverterImpl(@Lazy CommentConverter commentConverter) {
        this.commentConverter = commentConverter;
    }
    public TaskResponse convertToResponse(Task task){
        return new TaskResponse(task.getId(),task.getHeader(),task.getDescription()
                ,task.getStatus().getStringValueOfStatus(),task.getAuthor().getEmail(),task.getExecutor().getEmail(),
                task.getComments()==null?(Collections.emptyList()):
                        task.getComments().stream().map(commentConverter::convertToResponse).collect(Collectors.toList()),task.getCreatedAt());
    }
    public Task convertToTask(TaskRequest taskRequest){
        Task task = new Task();
        User user = new User();
        user.setEmail(taskRequest.getExecutorEmail());
        task.setHeader(taskRequest.getHeader());
        task.setDescription(taskRequest.getDescription());
        task.setExecutor(user); //todo добавить остальные параметры в сервисе
        return task;
    }
}
