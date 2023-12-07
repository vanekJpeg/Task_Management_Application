package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;


import java.util.List;
@Service
public interface TaskService {
    public List<TaskResponse> findAll(int page);
    public List<TaskResponse> findAllByAuthorName(String authorName);
    public List<TaskResponse> findAllByExecutorName(String executorName);
    public TaskResponse findOne(int id);
    public TaskResponse create(TaskRequest taskRequest);
    public List<CommentResponse> getAllTaskCommentsByTaskId(int taskId);
    public TaskResponse changeTaskStatus(int taskId, String newStatus);

    public TaskResponse update(int userId, TaskRequest taskRequest);
    public void delete(int id);
    public boolean isEnoughRules(int userId);
}
