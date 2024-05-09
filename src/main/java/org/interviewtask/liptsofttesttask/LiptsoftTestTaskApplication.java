package org.interviewtask.liptsofttesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"source.Repositories"})
@ComponentScan(basePackages = {"source.Services",})
@EntityScan(basePackages = {"source.Models"})
public class LiptsoftTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiptsoftTestTaskApplication.class, args);
    }
}
