package org.example.msteacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsTeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTeacherApplication.class, args);
    }

}
