package source.Parsing;

import picocli.CommandLine;
import source.Parsing.AddCategoryParsers.AddCategoryCommand;
import source.Parsing.AddGroupToCategoryParsers.AddGroupToExistingCategoryCommand;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryCommand;
import source.Parsing.AddTransactionCommandParsers.AddTransactionCommand;
import source.Parsing.RemoveCategoryParsers.RemoveCategoryCommand;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeCommand;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListCommand;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodCommand;

@CommandLine.Command(name = "command", description = "Main application command that delegates to subcommands",
        subcommands = {
                AddCategoryCommand.class,
                AddMccToExistingCategoryCommand.class,
                AddGroupToExistingCategoryCommand.class,
                RemoveCategoryCommand.class,
                ShowCategoriesListCommand.class,
                ShowCategoryAmountByCertainPeriodCommand.class,
                ShowAllExpensesByPeriodOfTimeCommand.class,
                AddTransactionCommand.class,
        })
public class MainCommand { }