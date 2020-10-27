package me.progbloom.datagrid.app.config.web.error;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("", e);
        return toErrorResponse(ErrorCode.TECHNICAL_ERROR);
    }

    @NotNull
    ErrorResponse toErrorResponse(@NotNull ErrorCode errorCode) {
        Objects.requireNonNull(errorCode);
        return new ErrorResponse(errorCode.name(), errorCode.getDescription());
    }

    static class ErrorResponse {

        private final String code;

        private final String description;

        public ErrorResponse(@NotNull String code, @NotNull String description) {
            this.code = Objects.requireNonNull(code);
            this.description = Objects.requireNonNull(description);
        }
    }
}
