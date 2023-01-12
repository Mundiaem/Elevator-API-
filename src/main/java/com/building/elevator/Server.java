package com.building.elevator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.building.elevator.*", "com.building.elevator.config"})
@EnableJpaRepositories(basePackages = "com.building.elevator.repository", entityManagerFactoryRef = "entityManagerFactory")
@EnableScheduling
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

}
