package source.Parsing.AddCategoryParsers;

import lombok.Getter;
import picocli.CommandLine;
import source.Parsing.Commandable;

import java.util.Collection;


/**
 * Command for adding category to the database
 * @see Commandable
 */
@Getter
@CommandLine.Command(name = "add-category", description = "Add category to the database")
public class AddCategoryCommand implements Commandable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-m", "--mcc"}, split = ",", description = "MCC codes of the transaction", required = true)
    private Collection<String> mccs;
}
