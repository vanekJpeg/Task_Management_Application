package ru.vanek.task_management_application.services;

import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;


import java.util.List;
@Service
public interface TaskService {
    public List<TaskResponse> findAll(int page);
    public List<TaskResponse> findAllByAuthorEmail(String authorEmail);
    public List<TaskResponse> findAllByExecutorEmail(String executorEmail);
    public TaskResponse findOne(int id);
    public TaskResponse create(TaskRequest taskRequest,String userEmail);
    public List<CommentResponse> getAllTaskCommentsByTaskId(int taskId);
    public TaskResponse changeTaskStatus(int taskId, String newStatus,String userEmail);

    public TaskResponse update(int userId, TaskRequest taskRequest,String userEmail);
    public void delete(int id,String userEmail);
    public boolean isAuthor(int userId, String userEmail);
    public boolean isExecutor(int userId, String userEmail);
}
