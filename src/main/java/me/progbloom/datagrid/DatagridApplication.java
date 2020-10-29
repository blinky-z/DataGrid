package me.progbloom.datagrid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DatagridApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatagridApplication.class, args);
    }

}