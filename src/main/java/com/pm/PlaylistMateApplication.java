package com.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class PlaylistMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaylistMateApplication.class, args);
	}

}
