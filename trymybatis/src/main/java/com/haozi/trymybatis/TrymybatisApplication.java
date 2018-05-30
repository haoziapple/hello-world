package com.haozi.trymybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TrymybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrymybatisApplication.class, args);
	}
}
