package me.progbloom.datagrid.app.data.exception;

import me.progbloom.datagrid.app.base.exception.ApplicationException;

public class WriteException extends ApplicationException {

    public WriteException(String message) {
        super(message);
    }

    public WriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
