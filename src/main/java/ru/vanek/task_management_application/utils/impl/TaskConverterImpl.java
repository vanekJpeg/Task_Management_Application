package ru.vanek.task_management_application.utils.impl;

import org.springframework.stereotype.Component;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.TaskConverter;
import ru.vanek.task_management_application.utils.UserConverter;

import java.util.stream.Collectors;

@Component
public class TaskConverterImpl implements TaskConverter {
    private final UserConverter userConverter;
    private final CommentConverter commentConverter;
    public TaskConverterImpl(UserConverter userConverter, CommentConverter commentConverter) {
        this.userConverter = userConverter;
        this.commentConverter = commentConverter;
    }
    public TaskResponse convertToResponse(Task task){
        return new TaskResponse(task.getId(),task.getHeader(),task.getDescription()
                ,task.getStatus().getStringValueOfStatus(),userConverter.convertUserToResponse(task.getAuthor()),
                userConverter.convertUserToResponse(task.getExecutor()),
                task.getComments().stream().map(commentConverter::convertToResponse).collect(Collectors.toList()),task.getCreatedAt());
    }
    public Task convertToTask(TaskRequest taskRequest){
        Task task = new Task();
        task.setHeader(taskRequest.getHeader());
        task.setDescription(taskRequest.getDescription());
        task.setExecutor(new User(taskRequest.getExecutorName())); //todo добавить остальные параметры в сервисе
        return task;
    }
}
