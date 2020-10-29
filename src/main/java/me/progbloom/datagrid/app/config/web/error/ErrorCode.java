package me.progbloom.datagrid.app.config.web.error;

import org.jetbrains.annotations.NotNull;

public enum ErrorCode {

    TECHNICAL_ERROR("Техническая ошибка сервера"),
    DATA_NOT_FOUND("Данные отсутствуют");

    ErrorCode(String description) {
        this.description = description;
    }

    private final String description;

    @NotNull
    public String getDescription() {
        return description;
    }
}
