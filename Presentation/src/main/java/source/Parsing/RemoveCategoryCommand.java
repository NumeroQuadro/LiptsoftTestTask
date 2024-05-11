package source.Parsing;

import lombok.Getter;
import picocli.CommandLine;

@Getter
@CommandLine.Command(name = "remove-category", description = "Remove category from the database")
public class RemoveCategoryCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @Override
    public void run() {
        System.out.println("Category name: " + categoryName);
    }
}
