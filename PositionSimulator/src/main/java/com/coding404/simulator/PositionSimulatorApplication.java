package com.coding404.simulator;

import com.coding404.simulator.component.PositionSimulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PositionSimulatorApplication {

	public static void main(String[] args) {
		
		//시작지점
		ConfigurableApplicationContext ctx = SpringApplication.run(PositionSimulatorApplication.class, args);

		Thread thread = new Thread(ctx.getBean(PositionSimulator.class));
		thread.start();
	}

}
