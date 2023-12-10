package ru.vanek.task_management_application.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema
public class ExceptionResponse {
    private String message;
    private Date timestamp;

}
