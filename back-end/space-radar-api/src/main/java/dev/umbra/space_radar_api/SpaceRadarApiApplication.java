package dev.umbra.space_radar_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Space Radar API application.
 * This class initializes the Spring Boot context, enables scheduled tasks, 
 * and loads environment properties.
 * <p>
 * Ponto de entrada principal para a aplicação Space Radar API.
 * Esta classe inicializa o contexto Spring Boot, habilita tarefas agendadas
 * e carrega as propriedades de ambiente.
 */
@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:env.properties")
public class SpaceRadarApiApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * <p>
	 * Método principal que inicia a aplicação Spring Boot.
	 * @param args command line arguments / argumentos de linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpaceRadarApiApplication.class, args);
	}

}
