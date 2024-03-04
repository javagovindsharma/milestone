package com.sap.milestone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MilestoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilestoneApplication.class, args);
	}

}
