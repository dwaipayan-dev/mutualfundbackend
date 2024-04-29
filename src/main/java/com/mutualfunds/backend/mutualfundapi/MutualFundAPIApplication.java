package com.mutualfunds.backend.mutualfundapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MutualFundAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutualFundAPIApplication.class, args);

	}
}
