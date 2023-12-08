package ru.vanek.task_management_application.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class CommentRequest {
    private String text;
    @JsonCreator
    public CommentRequest(@JsonProperty("text") String text) {
        this.text = text;
    }
}
