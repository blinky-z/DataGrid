package me.progbloom.datagrid.app.zookeeper;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZookeeperService {

    private final Logger log = LoggerFactory.getLogger(ZookeeperService.class);

    private final DiscoveryClient discovery;

    public ZookeeperService(DiscoveryClient discovery) {
        this.discovery = discovery;
    }

    @NotNull
    public List<ServiceInstance> listServices() {
        List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
        for (String name : discovery.getServices()) {
            instances.addAll(discovery.getInstances(name));
        }
        return instances;
    }
}
