package me.progbloom.datagrid.app.cluster;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Optional;

/**
 * Клиент для общения между нодами.
 * <p>
 * Протокол, используемый для общения, зависит от имплементации.
 */
interface InterCommunicationClient {

    /**
     * Читает данные с ноды по указанному ID.
     *
     * @param nodeHost адрес ноды
     * @param id       ID данных
     * @return данные или {@code Optional.empty()} если отсутствуют
     */
    @NotNull
    Optional<RemoteStorageValue> read(@NotNull final URI nodeHost, @NotNull final Long id);

    /**
     * Записывает данные в ноду с указанным ID.
     *
     * @param nodeHost адрес ноды
     * @param id       ID данных
     * @param data     данные
     */
    void write(@NotNull final URI nodeHost, @NotNull final Long id, @NotNull final String data);
}
