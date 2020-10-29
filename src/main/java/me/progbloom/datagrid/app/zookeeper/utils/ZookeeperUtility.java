package me.progbloom.datagrid.app.zookeeper.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServiceInstance;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

public final class ZookeeperUtility {

    private ZookeeperUtility() {
    }

    /**
     * Достает ID инстанса в Zookeeper из {@link ZookeeperServiceInstance}.
     * <p>
     * ID инстанса в Zookeeper отличается от ID инстанса в Spring Cloud Discovery System. При регистрации в Zookeeper
     * ID инстанса устанавливается равным {@link ApplicationContext#getId() context ID}, но в Spring Cloud Discovery
     * System {@link ServiceInstance#getInstanceId() ID инстанса} по-умолчанию инициализируется рандомным UUID.
     *
     * @param zookeeperInstance инстанс Zookeeper сервиса
     * @return ID инстанса
     */
    @NotNull
    public static String getZookeeperInstanceId(@NotNull ZookeeperServiceInstance zookeeperInstance) {
        Objects.requireNonNull(zookeeperInstance);
        return zookeeperInstance.getServiceInstance().getPayload().getId();
    }
}
