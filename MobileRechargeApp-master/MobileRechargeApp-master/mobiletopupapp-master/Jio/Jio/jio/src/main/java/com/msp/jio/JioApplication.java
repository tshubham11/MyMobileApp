
package com.msp.jio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "com.msp.jio.entity")
@ComponentScan(basePackageClasses = com.msp.jio.controller.JioController.class)
@ComponentScan(basePackageClasses = com.msp.jio.modelassembler.JioModelAssembler.class)
@EnableJpaRepositories("com.msp.jio.repository")
public class JioApplication {
	/**
	 * The main method for the msp.jio program
	 * @param args Not used
	 */
	public static void main(String[] args) {
		SpringApplication.run(JioApplication.class, args);
	}

}
