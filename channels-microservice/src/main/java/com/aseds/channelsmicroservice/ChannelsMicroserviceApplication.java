package com.aseds.channelsmicroservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChannelsMicroserviceApplication {

    /*private static final boolean IS_TEST = System.getProperty("spring.profiles.active") != null;

    static {
        if (!IS_TEST) {
            loadEnvFile();
        }
    }

    private static void loadEnvFile() {
        Dotenv dotenv = Dotenv.configure()
                .directory("channels-microservice")
                .filename(".env")
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }*/
    public static void main(String[] args) {
        SpringApplication.run(ChannelsMicroserviceApplication.class, args);
    }
}