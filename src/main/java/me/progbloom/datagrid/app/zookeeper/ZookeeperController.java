package me.progbloom.datagrid.app.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZookeeperController {

    @Autowired
    private ZookeeperService zookeeperService;

    @RequestMapping(value = "zoo/services", method = RequestMethod.GET)
    public List<ServiceInstance> listInstances() {
        return zookeeperService.listServices();
    }
}
