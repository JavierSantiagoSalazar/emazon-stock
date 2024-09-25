package com.pragma.emazon_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EmazonStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmazonStockApplication.class, args);
    }

}
