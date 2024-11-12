package com.coding404.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class PositionTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PositionTrackerApplication.class, args);
	}

}
