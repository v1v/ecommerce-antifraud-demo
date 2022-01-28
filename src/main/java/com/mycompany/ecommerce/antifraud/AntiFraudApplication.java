package com.mycompany.ecommerce.antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AntiFraudApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntiFraudApplication.class, args);
	}

	@GetMapping("/ecommerce")
	public String ecommerce(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
