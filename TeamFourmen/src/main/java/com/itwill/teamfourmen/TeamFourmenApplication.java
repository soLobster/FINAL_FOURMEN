package com.itwill.teamfourmen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TeamFourmenApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamFourmenApplication.class, args);
	}

}
