package org.example.msplanning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.example.msplanning")
public class MsPlanningApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPlanningApplication.class, args);
    }

}
