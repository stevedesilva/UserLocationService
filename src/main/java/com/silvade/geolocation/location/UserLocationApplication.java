package com.silvade.geolocation.location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserLocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLocationApplication.class, args);
	}

}
