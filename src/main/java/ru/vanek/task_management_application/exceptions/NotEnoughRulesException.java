package ru.vanek.task_management_application.exceptions;

public class NotEnoughRulesException extends  RuntimeException {
    public NotEnoughRulesException(String message) {
        super(message);
    }
}
