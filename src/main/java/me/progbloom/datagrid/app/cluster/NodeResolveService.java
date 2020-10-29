package me.progbloom.datagrid.app.cluster;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для обнаружения нод в кластере.
 */
@Service
class NodeResolveService {

    private final ZookeeperDiscoveryClient discoveryClient;
    private final ZookeeperDiscoveryProperties zookeeperDiscoveryProperties;
    private final ApplicationContext applicationContext;

    @Value("${spring.application.name}")
    private String serviceName;

    public NodeResolveService(ZookeeperDiscoveryClient discoveryClient,
                              ZookeeperDiscoveryProperties zookeeperDiscoveryProperties,
                              ApplicationContext applicationContext) {
        this.discoveryClient = discoveryClient;
        this.zookeeperDiscoveryProperties = zookeeperDiscoveryProperties;
        this.applicationContext = applicationContext;
    }

    /**
     * Ищет все ноды в кластере.
     *
     * @return ноды кластер (включая текущую)
     */
    @NotNull
    public List<ServiceInstance> resolveAllNodes() {
        return discoveryClient.getInstances(serviceName);
    }
}
