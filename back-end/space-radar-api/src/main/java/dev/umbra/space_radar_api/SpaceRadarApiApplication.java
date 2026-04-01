package dev.umbra.space_radar_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Space Radar API application.
 * This class initializes the Spring Boot context, enables scheduled tasks, 
 * and loads environment properties.
 */
@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:env.properties")
public class SpaceRadarApiApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpaceRadarApiApplication.class, args);
	}

}
