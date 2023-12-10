package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.exceptions.NotEnoughRulesException;
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
import java.util.NoSuchElementException;
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
    public List<TaskResponse> findAllByAuthorId(int authorId) {

        return tasksRepository.findAllByAuthorId(authorId).
                orElseThrow(()->new NoSuchElementException("Пользователя с идентификатором "+authorId+" - не существует"))
                .stream().map(taskConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> findAllByExecutorId(int executorId) {
        return tasksRepository.findAllByExecutorId(executorId).
                orElseThrow(()->new NoSuchElementException("Пользователя с идентификатором "+executorId+" - не существует"))
                .stream().map(taskConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public TaskResponse findOne(int id) {
        return taskConverter.convertToResponse(tasksRepository.findById(id).
                orElseThrow(()->new NoSuchElementException("Задачи с идентификатором "+id+" - не существует")));
    }

    @Override
    @Transactional
    public TaskResponse create(TaskRequest taskRequest,String authorEmail) {
        Task createdTask = new Task();
        String executorEmail;
        User author = usersRepository.findByEmail(authorEmail)
                .orElseThrow(()->new NoSuchElementException("Пользователя с emai'ом "+authorEmail+" - не существует")) ;
        User executor = usersRepository.findByEmail(executorEmail=(taskRequest.getExecutorEmail()))
                .orElseThrow(()->new NoSuchElementException("Пользователя с emai'ом "+executorEmail+" - не существует"));
        createdTask.setHeader(taskRequest.getHeader());
        createdTask.setDescription(taskRequest.getDescription());
        createdTask.setExecutor(executor);
        createdTask.setAuthor(author);
        createdTask.setCreatedAt(new Date());
        createdTask.setStatus(Status.WAITING);
        tasksRepository.save(createdTask);
        return taskConverter.convertToResponse(createdTask);
    }

    @Override
    public List<CommentResponse> getAllTaskCommentsByTaskId(int taskId) {
        return commentsRepository.findAllByCommentedTaskId(taskId)
                .orElseThrow(()->new NoSuchElementException("Задачи с идентификатором "+taskId+" - не существует"))
                .stream().map(commentConverter::convertToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponse changeTaskStatus(int taskId, String newStatus,String authorEmail) {
        if(isAuthor(taskId,authorEmail)|| isExecutor(taskId,authorEmail)){
            Task task= tasksRepository.findById(taskId).orElseThrow(()->new NoSuchElementException("Задачи с идентификатором "+taskId+" - не существует"));
            task.setStatus(Status.fromStringToStatus(newStatus));
            return taskConverter.convertToResponse(tasksRepository.save(task));
        }else throw new NotEnoughRulesException("Изменить статус может только атвор или исполнитель");

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
        }else throw new NotEnoughRulesException("Вы не являетесь автором задачи, поэтому не можете выполнить данную операцию");
    }

    @Override
    @Transactional
    public void delete(int taskId,String authorEmail) {
        if(isAuthor(taskId,authorEmail)){
        tasksRepository.deleteById(taskId);
        }throw new NotEnoughRulesException("Вы не являетесь автором задачи, поэтому не можете выполнить данную операцию");
    }

    @Override
    public boolean isAuthor(int taskId, String userEmail) {
        return userEmail.equals(tasksRepository.findById(taskId).
                orElseThrow(()->new NoSuchElementException("Пользователя с emai'ом "+userEmail+" - не существует")).getAuthor().getEmail());
    }

    @Override
    public boolean isExecutor(int taskId, String userEmail) {
        return userEmail.equals(tasksRepository.findById(taskId).
                orElseThrow(()->new NoSuchElementException("Пользователя с emai'ом "+userEmail+" - не существует")).getExecutor().getEmail());
    }
}
