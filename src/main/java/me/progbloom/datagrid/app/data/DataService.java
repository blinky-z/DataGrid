package me.progbloom.datagrid.app.data;

import me.progbloom.datagrid.app.data.exception.WriteException;
import me.progbloom.datagrid.app.data.storage.StorageValue;
import me.progbloom.datagrid.app.intercom.InterCommunicationService;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Сервис для управления данными в кластере.
 */
@Service
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    private final DistributedAtomicLong writeCounter;
    private final InterCommunicationService interCommunicationService;

    public DataService(DistributedAtomicLong writeCounter,
                       InterCommunicationService interCommunicationService) {
        this.writeCounter = writeCounter;
        this.interCommunicationService = interCommunicationService;
    }

    /**
     * Сохраняет данные.
     *
     * @param data данные
     * @return идентификатор, по которому можно получить сохраненные данные
     */
    @NotNull
    public Long save(@NotNull final String data) {
        Objects.requireNonNull(data);

        final Long id;
        try {
            AtomicValue<Long> value = writeCounter.increment();
            if (value.succeeded()) {
                id = value.postValue();
                log.debug("Счетчик записи инкрементирован. Предыдущее значение: {}, Текущее значение: {}",
                    value.preValue(), value.postValue());
            } else {
                throw new WriteException("Ошибка записи данных: превышено время ожидания инкрементирования счетчика");
            }
        } catch (Exception ex) {
            throw new WriteException("Ошибка записи данных", ex);
        }
        interCommunicationService.write(id, data);
        return id;
    }

    /**
     * Читает самые последние данные в кластере.
     *
     * @param id ID данных
     * @return данные или Optional.empty() если отсутствуют
     */
    public Optional<String> read(@NotNull Long id) {
        Objects.requireNonNull(id);

        List<StorageValue> results = interCommunicationService.read(id);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getMostFreshValue(results));
    }

    @NotNull
    private String getMostFreshValue(@NotNull List<StorageValue> remoteData) {
        Objects.requireNonNull(remoteData);
        if (remoteData.isEmpty()) {
            throw new IllegalArgumentException("Список результатов чтения пуст!");
        }
        return remoteData.stream()
            .sorted(Comparator.comparing(StorageValue::getTimestamp).reversed())
            .map(StorageValue::getValue)
            .findFirst().get();
    }
}
