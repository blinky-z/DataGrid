package me.progbloom.datagrid.app.config.data.storage;

import me.progbloom.datagrid.app.data.storage.HashMapStorage;
import me.progbloom.datagrid.app.data.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация хранилища.
 */
@Configuration
public class DataStorageConfiguration {

    @Bean
    public Storage stringStorage() {
        return new HashMapStorage();
    }
}
