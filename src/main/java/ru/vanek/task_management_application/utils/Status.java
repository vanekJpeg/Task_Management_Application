package ru.vanek.task_management_application.utils;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum Status {
    WAITING("в ожидании"), IN_PROCESS("выполняется"), COMPLETED("выполнено");

    private String status;

    Status(String status) {
        this.status = status;
    }
    public static Status fromStringToStatus(String status) {
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Указан неверный статус");
    }
    public String getStringValueOfStatus() {
        return status;
    }
}
