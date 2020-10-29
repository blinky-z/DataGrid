package me.progbloom.datagrid.app.data.storage;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Интерфейс Key-Value хранилища данных.
 *
 * @implNote имплементация должна быть Thread-Safe.
 */
public interface Storage {

    /**
     * Возвращает значение по указанному ключу.
     *
     * @param key ключ
     * @return значение или Optional.empty() если отсутствует
     */
    Optional<StorageValue> get(@NotNull final Long key);

    /**
     * Сохраняет данные с указанными ключом и значением.
     * <p>
     * Если данные по такому ключу уже сохранены, то они перезаписываются.
     *
     * @param key   ключ
     * @param value значение
     */
    void put(@NotNull final Long key, @NotNull final String value);

    /**
     * Удаляет данные по указанному ключу.
     * <p>
     * Если данных по указанному ключу не существует, то вызов не имеет эффекта.
     *
     * @param key ключ
     */
    void remove(@NotNull final Long key);
}
