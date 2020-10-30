package me.progbloom.datagrid.app.intercom.exception;

/**
 * Исключение, выбрасываемое при ошибке записи в ноду.
 */
public class InterCommunicationWriteException extends InterCommunicationException {

    public InterCommunicationWriteException(String message) {
        super(message);
    }

    public InterCommunicationWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
