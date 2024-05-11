package source.Parsing;

import lombok.Getter;
import picocli.CommandLine;

import java.util.Collection;

// add-group -n <category_name> -c <category_to_add> [-c <category to add>] ...
@Getter
@CommandLine.Command(name = "add-group", description = "Add group to existing category")
public class AddGroupToExistingCategoryCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, description = "Name of category", required = true)
    String categoryName;

    @CommandLine.Option(names = {"-c", "--category"}, split = ",", description = "Categories to add", required = true)
    private Collection<String> categories;

    @Override
    public void run() {
        System.out.println("Category name: " + categoryName);
        System.out.println("Categories: " + categories);
    }
}
