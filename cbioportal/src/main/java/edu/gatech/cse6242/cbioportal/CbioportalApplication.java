package edu.gatech.cse6242.cbioportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CbioportalApplication {

	public static void main(String[] args) {

		SpringApplication.run(CbioportalApplication.class, args);
	}

}
