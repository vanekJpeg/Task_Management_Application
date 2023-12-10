package ru.vanek.task_management_application.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CommentRequest {
    @NotEmpty
    @Size(min = 1,max = 200, message = "заголовок должен быть от 1 до 200 символов")
    private String text;
    @JsonCreator
    public CommentRequest(@JsonProperty("text") String text) {
        this.text = text;
    }
}
