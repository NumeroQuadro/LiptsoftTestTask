package source.Parsing.AddGroupToCategoryParsers;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import source.Parsing.Commandable;

import java.util.Collection;

@Getter
@Setter
@CommandLine.Command(name = "add-group", description = "Add group to existing category")
public class AddGroupToExistingCategoryCommand implements Commandable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-c", "--category"}, split = ",", description = "Categories to add", required = true)
    private Collection<String> categories;
}
