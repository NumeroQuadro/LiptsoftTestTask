package source.Parsing.RemoveCategoryParsers;

import lombok.Getter;
import picocli.CommandLine;
import source.Parsing.Commandable;

@Getter
@CommandLine.Command(name = "remove-category", description = "Remove category from the database")
public class RemoveCategoryCommand implements Commandable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;
}
