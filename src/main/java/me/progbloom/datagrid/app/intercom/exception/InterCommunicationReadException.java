package me.progbloom.datagrid.app.intercom.exception;

/**
 * Исключение, выбрасываемое при попытке чтения с ноды.
 */
public class InterCommunicationReadException extends InterCommunicationException {

    public InterCommunicationReadException(String message) {
        super(message);
    }

    public InterCommunicationReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
