package com.akash.genai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GenaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenaiApplication.class, args);
	}

}
