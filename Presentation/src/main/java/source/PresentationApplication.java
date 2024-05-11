package source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;
import source.Parsing.MainCommand;

@SpringBootApplication
public class PresentationApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationApplication.class, args);
    }
}

//`add category <name> <mcc> [mcc2] [mcc3] ...`
//`add-category -n <name> -m <mcc> [-m <mcc2>] [-m <mcc3>] ...`


//`add mcc to category <name> <mcc> [mcc2] [mcc3] ...`
//`add-mcc -n <category_name> -m <mcc> [-m <mcc2>] [-m <mcc3>] ...`
//
//
//`add group to category <name> <category to add> [category to add] ...`
//`add-group -n <category_name> -c <category_to_add> [-c <category to add>] ...`
//
//`remove category <name>`
//`remove-category -n <name>`
//
//`add transaction <name> <value> <month> [mcc]`
//
//
//`remove transaction <name> <value> <month>
//
//`show categories`
//
//`show <category name> by months`
//
//`show <category name> by months`
//`show -n <category name> -m <month>`
