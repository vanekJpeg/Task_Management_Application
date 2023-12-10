package ru.vanek.task_management_application.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Schema
public class ValidationErrorResponse {

    private final List<Violation> violations;

}


