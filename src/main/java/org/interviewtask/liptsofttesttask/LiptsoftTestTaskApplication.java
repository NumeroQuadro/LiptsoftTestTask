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
import source.Parsing.AddGroupToCategoryParsers.AddGroupToExistingCategoryCommand;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryCommand;
import source.Parsing.AddTransactionCommandParsers.AddTransactionCommand;
import source.Parsing.RemoveCategoryParsers.RemoveCategoryCommand;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeCommand;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListCommand;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodCommand;
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
            var commandLine = new CommandLine(new ShowCategoryAmountByCertainPeriodCommand());
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
