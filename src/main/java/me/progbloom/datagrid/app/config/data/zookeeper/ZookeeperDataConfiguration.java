package me.progbloom.datagrid.app.config.data.zookeeper;

import me.progbloom.datagrid.app.data.zookeeper.ZooKeeperHierarchy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperDataConfiguration {

    @Bean
    public DistributedAtomicLong writeCounter(CuratorFramework client) throws Exception {
        DistributedAtomicLong counter = new DistributedAtomicLong(client,
            ZooKeeperHierarchy.Synchronization.Counter.WRITE,
            new ExponentialBackoffRetry(10, 29));
        counter.initialize(0L);
        return counter;
    }
}
