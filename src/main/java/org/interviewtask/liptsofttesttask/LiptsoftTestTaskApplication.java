package org.interviewtask.liptsofttesttask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import picocli.CommandLine;
import source.CommandLineInterpreters.ConsoleCommandLineInterpreter;
import source.ConsoleMessageHandlers.ConsoleMessagesHandler;
import source.Parsing.AddCategoryParsers.AddCategoryCommand;
import source.Services.CategoryPerCategoryService;
import source.Services.CategoryService;
import source.Services.MccPerCategoryService;
import source.Services.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    CommandLineRunner commandLineRunner(ConsoleCommandLineInterpreter consoleCommandLineInterpreter, TransactionService transactionService, CategoryPerCategoryService categoryPerCategoryService, MccPerCategoryService mccPerCategoryService, CategoryService categoryService) {
        return args -> {
//            categoryService.addNewCategory("1");
//            categoryService.addNewCategory("2");
//            categoryService.addNewCategory("3");
//            categoryService.addNewCategory("4");
//            mccPerCategoryService.addNewMccToCategory("1", "1111");
//            mccPerCategoryService.addNewMccToCategory("2", "2222");
//            mccPerCategoryService.addNewMccToCategory("3", "3333");
//            mccPerCategoryService.addNewMccToCategory("4", "4444");
//            categoryPerCategoryService.addGroupToCategory("1", "2");
//            categoryPerCategoryService.addGroupToCategory("2", "3");
//            categoryPerCategoryService.addGroupToCategory("1", "4");
//            transactionService.addNewTransaction(new BigDecimal(100), LocalDate.of(2022, 12, 1), "1111");
//            transactionService.addNewTransaction(new BigDecimal(150), LocalDate.of(2024, 12, 2), "2222");
//            transactionService.addNewTransaction(new BigDecimal(200), LocalDate.of(2023, 12, 3), "3333");
//            transactionService.addNewTransaction(new BigDecimal(1000), LocalDate.of(2023, 12, 3), "4444");
//            transactionService.getTransactionsSumByCategoryInRequestedMonth(12).forEach((k, v) -> System.out.println(k + " " + v));
            consoleCommandLineInterpreter.processArgumentLines(args);

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
