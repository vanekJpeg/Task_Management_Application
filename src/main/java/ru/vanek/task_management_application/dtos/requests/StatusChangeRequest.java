package ru.vanek.task_management_application.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatusChangeRequest {
    private String status;
    @JsonCreator
    public StatusChangeRequest(@JsonProperty("status") String status) {
        this.status = status;
    }
}
