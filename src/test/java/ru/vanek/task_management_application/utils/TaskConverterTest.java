package ru.vanek.task_management_application.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vanek.task_management_application.dtos.requests.TaskRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.dtos.responses.TaskResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.models.Task;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.impl.TaskConverterImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TaskConverterTest {
    @InjectMocks
    TaskConverterImpl converter;
    @Mock
    CommentConverter commentConverter;
    @Test
    void convertToResponse() {
        //given
        var author = new User
                (1,"Author", "AuthorPassword_1", "Author@mail.ru");
        var executor = new User
                (2,"Executor", "ExecutorPassword_1", "Executor@mail.ru");
        var comment = new Comment();
        List<Comment> comments = List.of(comment);
        CommentResponse commentResponse = new CommentResponse(0,null,null,null);
        TaskResponse response1 = new TaskResponse();
        response1.setId(1);
        response1.setHeader("Task1");
        response1.setDescription("Task1");
        response1.setStatusDescription("выполнено");
        response1.setAuthorName("Author@mail.ru");
        response1.setExecutorName("Executor@mail.ru");
        response1.setComments(List.of(commentResponse));

        Task task1 = new Task();
        task1.setId(1);
        task1.setHeader("Task1");
        task1.setDescription("Task1");
        task1.setStatus(Status.COMPLETED);
        task1.setAuthor(author);
        task1.setExecutor(executor);
        task1.setComments(comments);
        doReturn(commentResponse).when(this.commentConverter).convertToResponse(comment);
        //when
        var responseEntity1 = this.converter.convertToResponse(task1);
        //then
        assertNotNull(responseEntity1);
        assertEquals(response1, responseEntity1);
    }

    @Test
    void convertToTask() {
        //given
        TaskRequest request = new TaskRequest("text","desc","email@mail.ru");
        User executor =new User();
        executor.setEmail("email@mail.ru");
        Task task = new Task();
        task.setId(0);
        task.setHeader("text");
        task.setDescription("desc");
        task.setStatus(null);
        task.setExecutor(executor);
        //when
        var responseEntity1 = this.converter.convertToTask(request);
        //then
        assertNotNull(responseEntity1);
        assertEquals(task, responseEntity1);
    }
}