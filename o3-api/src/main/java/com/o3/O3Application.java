package com.o3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class O3Application {

	public static void main(String[] args) {
		SpringApplication.run(O3Application.class, args);
	}

}
