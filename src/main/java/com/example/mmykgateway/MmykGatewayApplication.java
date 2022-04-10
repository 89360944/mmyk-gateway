package com.example.mmykgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MmykGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmykGatewayApplication.class, args);
    }

}
