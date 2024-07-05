package com.example.farmcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FarmCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmCollectorApplication.class, args);
	}

}
