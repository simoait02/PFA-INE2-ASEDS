package com.aseds.blogsmicroservice.blogs_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BlogsMicroserviceApplication {
	static {
		Dotenv dotenv = Dotenv.configure()
				.directory("blogs-microservice")
				.filename(".env")
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogsMicroserviceApplication.class, args);
	}

}
