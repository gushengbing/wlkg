package com.wlkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication//(scanBasePackages = {"com.wlkg.service" })
@EnableDiscoveryClient
@EnableFeignClients
public class WlkgCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WlkgCartApplication.class, args);
    }
}
