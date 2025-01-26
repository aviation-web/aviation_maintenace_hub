package com.aeromaintenance.aero_maint_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = " com.aeromaintenance")
@EnableJpaRepositories(basePackages = "com.aeromaintenance")
@SpringBootApplication(scanBasePackages = "com.aeromaintenance")

//@SpringBootApplication
public class AeroMaintCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeroMaintCoreApplication.class, args);
		System.out.println("Application Run..");
	}

}
