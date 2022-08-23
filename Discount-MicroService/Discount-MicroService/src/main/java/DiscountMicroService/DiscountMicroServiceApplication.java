package DiscountMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DiscountMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountMicroServiceApplication.class, args);
	}

}
