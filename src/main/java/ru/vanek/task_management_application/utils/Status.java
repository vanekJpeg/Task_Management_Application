package ru.vanek.task_management_application.utils;

public enum Status {
    WAITING("в ожидании"), IN_PROCESS("выполнятеся"), COMPLETED("выполнен");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStringValueOfStatus() {
        return status;
    }
}
