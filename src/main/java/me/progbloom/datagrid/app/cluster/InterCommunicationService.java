package me.progbloom.datagrid.app.cluster;

import me.progbloom.datagrid.app.cluster.exception.InterCommunicationReadException;
import me.progbloom.datagrid.app.cluster.exception.InterCommunicationWriteException;
import me.progbloom.datagrid.app.data.storage.StorageValue;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис для общения между нодами кластера.
 */
@Service
public class InterCommunicationService {

    private final Logger log = LoggerFactory.getLogger(InterCommunicationService.class);

    private final InterCommunicationHttpBlockingClient nodeCommunicationHttpBlockingClient;
    private final NodeResolveService nodeResolveService;

    public InterCommunicationService(InterCommunicationHttpBlockingClient nodeCommunicationHttpBlockingClient,
                                     NodeResolveService nodeResolveService) {
        this.nodeCommunicationHttpBlockingClient = nodeCommunicationHttpBlockingClient;
        this.nodeResolveService = nodeResolveService;
    }

    /**
     * Реплицирует данные во все ноды кластера.
     *
     * @param id   ID данных
     * @param data данные
     */
    public void write(@NotNull final Long id, @NotNull final String data) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(data);

        final List<ServiceInstance> nodes = nodeResolveService.resolveAllNodes();
        final int clusterSize = nodes.size();
        int successCounter = 0;

        for (ServiceInstance node : nodes) {
            try {
                nodeCommunicationHttpBlockingClient.write(node.getUri(), id, data);
                ++successCounter;
            } catch (InterCommunicationWriteException ex) {
                log.error("Ошибка записи данных. ID данных: {}. Адрес ноды: {}", id, node.getUri(), ex);
            }
        }
        log.info("Запись данных в ноды завершена. ID данных: {}. Успешно: {}/{}", id, successCounter, clusterSize);
    }

    /**
     * Читает данные со всех нод кластера.
     *
     * @param id ID данных
     */
    @NotNull
    public List<StorageValue> read(@NotNull final Long id) {
        Objects.requireNonNull(id);

        final List<ServiceInstance> nodes = nodeResolveService.resolveAllNodes();
        final int clusterSize = nodes.size();
        int successCounter = 0;

        final List<StorageValue> result = new ArrayList<>(clusterSize);
        for (ServiceInstance node : nodes) {
            try {
                Optional<RemoteStorageValue> remoteValue = nodeCommunicationHttpBlockingClient.read(node.getUri(), id);
                if (remoteValue.isPresent()) {
                    result.add(new StorageValue(
                        remoteValue.get().getValue(), Instant.ofEpochMilli(remoteValue.get().getEpochMilli())));
                }
                ++successCounter;
            } catch (InterCommunicationReadException ex) {
                log.error("Ошибка чтения данных. ID данных: {}. Адрес ноды: {}", id, node.getUri(), ex);
            }
        }
        log.info("Чтение с нод завершено. ID данных: {}. Успешно: {}/{}", id, successCounter, clusterSize);
        return result;
    }

    /**
     * Откатывает изменения в случае ошибки записи.
     *
     * @param id ID данных
     */
    public void rollback(@NotNull final Long id) {
        Objects.requireNonNull(id);
        // TODO: implement rollback
    }
}
