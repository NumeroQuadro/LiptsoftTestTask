package source.Parsing;

import lombok.Getter;
import picocli.CommandLine;

import java.util.Collection;
import java.util.concurrent.Callable;

//`add category <name> <mcc> [mcc2] [mcc3] ...`
//`add-category -n <name> -m <mcc> [-m <mcc2>] [-m <mcc3>] ...`
@Getter
@CommandLine.Command(name = "add-category", description = "Add category to the database")
public class AddCategoryCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-m", "--mcc"}, split = ",", description = "MCC codes of the transaction", required = true)
    private Collection<String> mccs;

    @Override
    public void run() {
        System.out.println("Category name: " + categoryName);
    }
}
