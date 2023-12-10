package ru.vanek.task_management_application.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.vanek.task_management_application.utils.Status;

@Data
@Schema(implementation = Status.class)
public class StatusChangeRequest {
    @NotEmpty
    private String status;
    @JsonCreator
    public StatusChangeRequest(@JsonProperty("status") String status) {
        this.status = status;
    }
}
