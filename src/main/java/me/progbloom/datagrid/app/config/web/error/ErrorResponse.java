package me.progbloom.datagrid.app.config.web.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Структура ошибки.
 */
public class ErrorResponse {

    @JsonProperty("code")
    private final String code;

    @JsonProperty("description")
    private final String description;

    private ErrorResponse(@NotNull ErrorCode errorCode) {
        this.code = errorCode.name();
        this.description = errorCode.getDescription();
    }

    @NotNull
    public static ErrorResponse of(@NotNull ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}
