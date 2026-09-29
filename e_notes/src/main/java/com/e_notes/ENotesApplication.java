package com.e_notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ENotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ENotesApplication.class, args);
	}

}
