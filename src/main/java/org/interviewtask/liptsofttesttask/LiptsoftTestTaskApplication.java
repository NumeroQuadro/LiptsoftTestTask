package org.interviewtask.liptsofttesttask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import source.CommandLineInterpreters.ConsoleCommandLineInterpreter;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"source.Repositories"})
@ComponentScan(basePackages = {"source.Services", "source.Models", "source.Parsing", "source.CommandLineInterpreters", "source.ConsoleMessageHandlers"})
@EntityScan(basePackages = {"source.Models"})
public class LiptsoftTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiptsoftTestTaskApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ConsoleCommandLineInterpreter consoleCommandLineInterpreter) {
        return args -> {
            while(true) {
                System.out.println("Enter command:");
                Scanner scanner = new Scanner(System.in);
                String argumentLine = scanner.nextLine();
                String[] splitLine = argumentLine.split(" ");
                consoleCommandLineInterpreter.processArgumentLines(splitLine);
            }
        };
    }
}
