package source.Parsing.AddMccToCategoryParsers;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import source.Parsing.Commandable;

import java.util.Collection;

@Getter
@Setter
@CommandLine.Command(name = "add-mcc", description = "Add MCC to existing category")
public class AddMccToExistingCategoryCommand implements Commandable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;
    @CommandLine.Option(names = {"-m", "--mcc"}, split = ",", description = "MCC codes of the transaction", required = true)
    private Collection<String> mccs;
}
