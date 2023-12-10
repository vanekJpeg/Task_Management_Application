package ru.vanek.task_management_application.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.services.CommentsService;
import ru.vanek.task_management_application.services.TaskService;
import ru.vanek.task_management_application.utils.TaskConverter;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    TaskService taskService;
    @Mock
    MessageSource messageSource;
    @Mock
    CommentsService commentsService;
    @InjectMocks
    TaskController taskController;
    private static List<TaskResponse> getTaskResponses() {
        var author = new User
                (1,"Author", "AuthorPassword_1", "Author@mail.ru");
        var executor = new User
                (2,"Executor", "ExecutorPassword_1", "Executor@mail.ru");

        TaskResponse task1 = new TaskResponse();
        task1.setId(1);
        task1.setHeader("Task1");
        task1.setDescription("Task1");
        task1.setStatusDescription("Выполнено");
        task1.setExecutorName(author.getEmail());
        task1.setExecutorName(executor.getEmail());

        TaskResponse task2 = new TaskResponse();
        task2.setId(2);
        task2.setHeader("Task2");
        task2.setDescription("Task2");
        task2.setStatusDescription("Выполнено");
        task2.setAuthorName(author.getEmail());
        task2.setAuthorName(executor.getEmail());


        return (List.of(task1,task2));
    }
    private static List<CommentResponse> getCommentResponses() {
        var author = new User
                (1,"Author", "AuthorPassword_1", "Author@mail.ru");
        var comment1 = new CommentResponse(1,"Comment1",author.getEmail(),new Date());
        var comment2 = new CommentResponse(2,"Comment2",author.getEmail(),new Date());
        return (List.of(comment1,comment2));
    }
    private static TaskRequest getTaskTaskRequest() {
        return  new TaskRequest("Header","Desc","Email@email");
    }
    @Test
    void getAllTasks_ReturnsValidResponseEntity() {
        // given

        List<TaskResponse> taskResponses = getTaskResponses();
        doReturn(taskResponses).when(this.taskService).findAll(1);

        // when
        var responseEntity = this.taskController.getAllTasks(1);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(taskResponses, responseEntity.getBody());
    }

    @Test
    void getTaskById_ReturnsValidResponseEntity() {
        // given
        List<TaskResponse> taskResponses = getTaskResponses();

        doReturn(taskResponses.get(0)).when(this.taskService).findOne(taskResponses.get(0).getId());

        // when
        var responseEntity = this.taskController.getTaskById(taskResponses.get(0).getId());

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(taskResponses.get(0), responseEntity.getBody());
    }

    @Test
    void createTask_ReturnsValidResponseEntity() {
        // given
        List<TaskResponse> taskResponses = getTaskResponses();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return taskResponses.get(1).getAuthorName();
            }
        };
        TaskRequest taskRequest= getTaskTaskRequest();
        doReturn(taskResponses.get(1)).when(this.taskService).create(taskRequest,principal.getName());
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        // when
        var responseEntity = this.taskController.createTask(taskRequest,uriComponentsBuilder,principal);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(taskResponses.get(1), responseEntity.getBody());
    }




    @Test
    void publishComment_ReturnsValidResponseEntity() {
        // given
        List<CommentResponse> commentResponses = getCommentResponses();
        CommentRequest commentRequest = new CommentRequest(commentResponses.get(1).getText());
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return commentResponses.get(1).getAuthorEmail();
            }
        };
        doReturn(commentResponses.get(1)).when(this.commentsService)
                .publishComment(commentRequest,1,principal.getName());
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();

        // when
        var responseEntity = this.taskController.publishComment(commentRequest,uriComponentsBuilder,1,principal);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(commentResponses.get(1), responseEntity.getBody());
    }

    @Test
    void getCommentById_ReturnsValidResponseEntity() {
        // given
        List<CommentResponse> commentResponses = getCommentResponses();

        doReturn(commentResponses.get(0)).when(this.commentsService).findOne(commentResponses.get(0).getId());

        // when
        var responseEntity = this.taskController.getCommentById(commentResponses.get(0).getId(),1);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(commentResponses.get(0), responseEntity.getBody());
    }

}