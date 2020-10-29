package me.progbloom.datagrid.app.data.storage;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Objects;

/**
 * Данные, сохраненные в хранилище.
 *
 */
public class StorageValue {

    /**
     * Данные.
     */
    @NotNull
    private final String value;

    /**
     * Время сохранения данных в UTC.
     */
    @NotNull
    private final Instant timestamp;

    public StorageValue(@NotNull String value) {
        this(value, Instant.now());
    }

    public StorageValue(@NotNull String value, @NotNull Instant timestamp) {
        this.value = Objects.requireNonNull(value);
        this.timestamp = Objects.requireNonNull(timestamp);
    }

    @NotNull
    public String getValue() {
        return value;
    }

    @NotNull
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageValue that = (StorageValue) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "StorageValue{" +
            "value=" + value +
            ", timestamp=" + timestamp +
            '}';
    }
}
