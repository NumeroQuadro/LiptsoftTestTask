package source.Parsing;

import picocli.CommandLine;

@CommandLine.Command(name = "command", description = "Main application command that delegates to subcommands",
        subcommands = { AddCategoryCommand.class,
                AddMccToExistingCategoryCommand.class,
                AddGroupToExistingCategoryCommand.class,
                RemoveCategoryCommand.class,
                ShowCategoriesListCommand.class,
                ShowCategoryAmountByCertainPeriodCommand.class,})
public class MainCommand { }