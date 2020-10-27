package me.progbloom.datagrid.app.base.exception;

public class WriteException extends ApplicationException {

    public WriteException(String message) {
        super(message);
    }

    public WriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
