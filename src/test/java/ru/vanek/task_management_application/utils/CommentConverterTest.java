package ru.vanek.task_management_application.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vanek.task_management_application.dtos.requests.CommentRequest;
import ru.vanek.task_management_application.dtos.responses.CommentResponse;
import ru.vanek.task_management_application.models.Comment;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.utils.impl.CommentConverterImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CommentConverterTest {
    @InjectMocks
    CommentConverterImpl converter;
    @Test
    void convertToResponse() {
        //given
        Comment comment = new Comment(1,"text",new Date());
        User user = new User
                (1,"Author1", "AuthorPassword_1", "Author1@mail.ru");
        comment.setAuthor(user);
        CommentResponse commentResponse= new CommentResponse(comment.getId(),comment.getText(),comment.getAuthor().getEmail(),comment.getCreatedAt());
        //then
        var responseEntity1 = this.converter.convertToResponse(comment);
        //then
        assertNotNull(responseEntity1);
        assertEquals(commentResponse, responseEntity1);
    }

    @Test
    void convertToComment() {
        //given
        CommentRequest commentRequest = new CommentRequest("text");
        Comment comment = new Comment(0,"text",null);
        //then
        var responseEntity1 = this.converter.convertToComment(commentRequest);
        //then
        assertNotNull(responseEntity1);
        assertEquals(comment, responseEntity1);
    }
}