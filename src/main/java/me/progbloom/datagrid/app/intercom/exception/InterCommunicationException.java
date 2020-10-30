package me.progbloom.datagrid.app.intercom.exception;

import me.progbloom.datagrid.app.base.exception.ApplicationException;

/**
 * Базовый класс для всех исключений при общении между нодами кластера.
 */
public class InterCommunicationException extends ApplicationException {

    public InterCommunicationException(String message) {
        super(message);
    }

    public InterCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
