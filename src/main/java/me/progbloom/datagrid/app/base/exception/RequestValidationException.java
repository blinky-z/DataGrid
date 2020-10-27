package me.progbloom.datagrid.app.base.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * Исключение, выбрасываемое при невалидном запросе.
 */
public class RequestValidationException extends ApplicationException {

    @NotNull
    private final BindingResult bindingResult;

    public RequestValidationException(@NotNull BindingResult bindingResult) {
        super();
        this.bindingResult = Objects.requireNonNull(bindingResult);
    }

    @Override
    public String toString() {
        return "Невалидный запрос. Ошибки: " + bindingResult.getAllErrors();
    }
}
