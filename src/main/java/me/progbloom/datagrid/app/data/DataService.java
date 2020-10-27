package me.progbloom.datagrid.app.data;

import me.progbloom.datagrid.app.base.exception.WriteException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис для управления данными.
 */
@Service
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    private final CuratorFramework client;
    private final DistributedAtomicLong writeCounter;

    private HashMap<Long, String> data;

    public DataService(CuratorFramework client,
                       DistributedAtomicLong writeCounter) {
        this.client = client;
        this.writeCounter = writeCounter;
        this.data = new HashMap<>();
    }

    /**
     * Сохраняет данные.
     *
     * @param s данные
     * @return идентификатор, по которому можно получить сохраненные данные
     */
    @NotNull
    public Long save(@NotNull String s) {
        Objects.requireNonNull(s);

        Long counter;
        try {
            AtomicValue<Long> value = writeCounter.increment();
            if (value.succeeded()) {
                counter = value.postValue();
                log.debug("Счетчик записи инкрементирован. Предыдущее значение: {}, Текущее значение: {}",
                    value.preValue(), value.postValue());
            } else {
                throw new WriteException("Ошибка записи данных: превышено время ожидания инкрементирования счетчика");
            }
        } catch (Exception e) {
            throw new WriteException("Ошибка записи данных", e);
        }
        data.put(counter, s);
        return counter;
    }

    public Optional<String> read(@NotNull Long id) {
        Objects.requireNonNull(id);
        return Optional.ofNullable(data.get(id));
    }
}
