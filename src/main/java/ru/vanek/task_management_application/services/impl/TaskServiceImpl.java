package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.repositories.CommentsRepository;
import ru.vanek.task_management_application.repositories.TasksRepository;
import ru.vanek.task_management_application.repositories.UsersRepository;
import ru.vanek.task_management_application.services.TaskService;
import ru.vanek.task_management_application.utils.CommentConverter;
import ru.vanek.task_management_application.utils.Status;
import ru.vanek.task_management_application.utils.TaskConverter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final CommentConverter commentConverter;
    private final TaskConverter taskConverter;


    @Override
    public List<TaskResponse> findAll(int page) {
        return tasksRepository.findAll(PageRequest.of(page,10)).getContent()
                .stream().map(taskConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findAllByAuthorEmail(String authorEmail) {
        return tasksRepository.findAllByAuthorEmail(authorEmail).stream().map(taskConverter::convertToResponse).collect(Collectors.toList()); //todo exception
    }

    @Override
    public List<TaskResponse> findAllByExecutorEmail(String executorEmail) {
        return tasksRepository.findAllByAuthorEmail(executorEmail).stream().map(taskConverter::convertToResponse).collect(Collectors.toList()); //todo exception;
    }

    @Override
    public TaskResponse findOne(int id) {
        return taskConverter.convertToResponse(tasksRepository.findById(id).orElseThrow());//todo exception;
    }

    @Override
    @Transactional
    public TaskResponse create(TaskRequest taskRequest,String authorEmail) {
        Task createdTask = taskConverter.convertToTask(taskRequest);
        User author = usersRepository.findByEmail(authorEmail).orElseThrow() ;//todo exception;
        User executor = usersRepository.findByEmail(createdTask.getExecutor().getEmail()).orElseThrow();//todo exception;
        createdTask.setExecutor(executor);
        createdTask.setAuthor(author);
        createdTask.setCreatedAt(new Date());
        createdTask.setStatus(Status.WAITING);
        return taskConverter.convertToResponse(createdTask);
    }

    @Override
    public List<CommentResponse> getAllTaskCommentsByTaskId(int taskId) {
        return commentsRepository.findAllByCommentedTaskId(taskId).orElseThrow()
                .stream().map(commentConverter::convertToResponse).collect(Collectors.toList());//todo exception;
    }

    @Override
    @Transactional
    public TaskResponse changeTaskStatus(int taskId, String newStatus,String authorEmail) {
        if(isAuthor(taskId,authorEmail)||isExecutor(taskId,authorEmail)){
            Task task= tasksRepository.findById(taskId).orElseThrow();
            task.setStatus(Status.valueOf(newStatus));
            return taskConverter.convertToResponse(tasksRepository.save(task));
        }else throw new RuntimeException();//todo exception;

    }

    @Override
    @Transactional
    public TaskResponse update(int taskId, TaskRequest taskRequest,String authorEmail) {
        if(isAuthor(taskId,authorEmail)){
            Task task= tasksRepository.findById(taskId).orElseThrow();
            task.setDescription(taskRequest.getDescription());
            task.setExecutor(usersRepository.findByEmail(taskRequest.getExecutorEmail()).orElseThrow());
            task.setHeader(taskRequest.getHeader());
            return taskConverter.convertToResponse(tasksRepository.save(task));
        }else throw new RuntimeException();//todo exception;
    }

    @Override
    @Transactional
    public void delete(int taskId,String authorEmail) {
        if(isAuthor(taskId,authorEmail)){
        tasksRepository.deleteById(taskId);
        }else throw new RuntimeException();//todo exception;
    }

    @Override
    public boolean isAuthor(int taskId, String userEmail) {
        return userEmail.equals(tasksRepository.findById(taskId).orElseThrow().getAuthor().getEmail());//todo exception;
    }

    @Override
    public boolean isExecutor(int taskId, String userEmail) {
        return userEmail.equals(tasksRepository.findById(taskId).orElseThrow().getExecutor().getEmail());//todo exception;
    }
}
