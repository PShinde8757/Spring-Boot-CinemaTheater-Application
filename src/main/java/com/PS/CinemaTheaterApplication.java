package com.PS;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class  CinemaTheaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaTheaterApplication.class, args);
		log.info("Cinema Theater Application is Starting... ");
	}

}
