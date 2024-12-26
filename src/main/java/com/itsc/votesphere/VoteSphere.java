package com.itsc.votesphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class VoteSphere {

	public static void main(String[] args) {
		
		SpringApplication.run(VoteSphere.class, args);
	}


    
}

