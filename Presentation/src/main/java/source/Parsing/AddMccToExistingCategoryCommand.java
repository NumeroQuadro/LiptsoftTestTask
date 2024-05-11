package source.Parsing;

import lombok.Getter;
import picocli.CommandLine;

import java.util.Collection;

// add mcc to category <name> <mcc> [mcc2] [mcc3] ...
@Getter
@CommandLine.Command(name = "add-mcc", description = "Add MCC to existing category")
public class AddMccToExistingCategoryCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;
    @CommandLine.Option(names = {"-m", "--mcc"}, split = ",", description = "MCC codes of the transaction", required = true)
    private Collection<String> mccs;

    @Override
    public void run() {
        System.out.println("Category name: " + categoryName);
        System.out.println("MCCs: " + mccs);
    }
}
