package me.progbloom.datagrid.app.data.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Имплементация Key-Value хранилища данных на основе {@link ConcurrentHashMap}.
 */
public final class HashMapStorage implements Storage {

    private final ConcurrentHashMap<Long, StorageValue> data;

    public HashMapStorage() {
        this.data = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<StorageValue> get(@NotNull Long key) {
        Objects.requireNonNull(key);
        return Optional.ofNullable(data.get(key));
    }

    @Override
    public void put(@NotNull Long key, @NotNull String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        data.put(key, new StorageValue(value));
    }

    @Override
    public void remove(@NotNull Long key) {
        data.remove(key);
    }

    @Override
    public String toString() {
        return "HashMapStorage{" +
            "size=" + data.size() +
            '}';
    }
}
