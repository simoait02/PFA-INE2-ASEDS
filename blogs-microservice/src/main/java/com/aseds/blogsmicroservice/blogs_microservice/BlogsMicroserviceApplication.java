package com.aseds.blogsmicroservice.blogs_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BlogsMicroserviceApplication {
	private static final boolean IS_TEST = System.getProperty("spring.profiles.active") != null;
	static {
		if (!IS_TEST) {
			loadEnvFile();
		}
	}
	private static void loadEnvFile() {
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
