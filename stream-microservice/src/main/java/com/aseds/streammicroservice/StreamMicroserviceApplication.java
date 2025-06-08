package com.aseds.streammicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamMicroserviceApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("stream-microservice")
                .filename(".env")
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        SpringApplication.run(StreamMicroserviceApplication.class, args);
    }

}
