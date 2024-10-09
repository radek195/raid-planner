package com.example.raid_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RaidPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaidPlannerApplication.class, args);
	}

}
