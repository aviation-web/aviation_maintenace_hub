package com.aeromaintenance.aero_maint_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = "com.aeromaintenance")
@EnableJpaRepositories(basePackages = "com.aeromaintenance")
@SpringBootApplication(scanBasePackages = "com.aeromaintenance")
public class AeroMaintCoreApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AeroMaintCoreApplication.class, args);
		System.out.println("Application Run..");	
   }
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AeroMaintCoreApplication.class);
    }
}
	
