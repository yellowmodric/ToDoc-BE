package com.solinone.todoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodocApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodocApplication.class, args);
	}

}
