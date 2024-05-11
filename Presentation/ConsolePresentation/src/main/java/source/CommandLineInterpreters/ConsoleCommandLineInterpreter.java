package source.CommandLineInterpreters;

import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import source.ConsoleMessageHandlers.ConsoleMessagesHandler;
import source.Interpreters.Interpretable;
import source.Parsing.AddCategoryParsers.AddCategoryCommand;
import source.Parsing.AddCategoryParsers.AddCategoryCommandHandler;
import source.Parsing.AddGroupToCategoryParsers.AddGroupToExistingCategoryCommand;
import source.Parsing.AddGroupToCategoryParsers.AddGroupToExistingCategoryHandler;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryCommand;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryHandler;
import source.Parsing.AddTransactionCommandParsers.AddTransactionCommand;
import source.Parsing.AddTransactionCommandParsers.AddTransactionHandler;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.MainCommand;
import source.Parsing.ParsingResults.ParsingResult;
import source.Parsing.RemoveCategoryParsers.RemoveCategoryCommand;
import source.Parsing.RemoveCategoryParsers.RemoveCategoryHandler;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeCommand;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeHandler;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListCommand;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListHandler;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodCommand;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodHandler;

public class ConsoleCommandLineInterpreter implements Interpretable {
    @Autowired
    private ConsoleMessagesHandler consoleMessagesHandler;

    @Override
    public void processArgumentLines(String[] args) {
        CommandLine commandLine = new CommandLine(new MainCommand());
        CommandLine.ParseResult parseResult = commandLine.parseArgs(args);

        if (!parseResult.hasSubcommand()) {
            consoleMessagesHandler.handleFailureMessage(commandLine.getUsageMessage());
            return;
        }

        CommandLine.ParseResult subResult = parseResult.subcommand();
        var command = subResult.commandSpec().userObject();
        var handlingResult = handleCommand(command);

        if (handlingResult instanceof HandlingResult.Success success) {
            consoleMessagesHandler.handleSuccessMessage(success.getSuccessMessage());
        }
        else if (handlingResult instanceof HandlingResult.Failure failure) {
            consoleMessagesHandler.handleFailureMessage(failure.getFailureMessage());
        }
    }

    private HandlingResult handleCommand(Object command) {
        if (command instanceof AddCategoryCommand addCategoryCommand) {
            return new AddCategoryCommandHandler().handleCommand(addCategoryCommand);
        }
        else if (command instanceof AddGroupToExistingCategoryCommand addGroupToExistingCategoryCommand) {
            return new AddGroupToExistingCategoryHandler().handleCommand(addGroupToExistingCategoryCommand);
        }
        else if (command instanceof AddMccToExistingCategoryCommand addMccToExistingCategoryCommand) {
            return new AddMccToExistingCategoryHandler().handleCommand(addMccToExistingCategoryCommand);
        }
        else if (command instanceof RemoveCategoryCommand removeCategoryCommand) {
            return new RemoveCategoryHandler().handleCommand(removeCategoryCommand);
        }
        else if (command instanceof ShowCategoriesListCommand showCategoriesListCommand) {
            return new ShowCategoriesListHandler().handleCommand(showCategoriesListCommand);
        }
        else if (command instanceof ShowCategoryAmountByCertainPeriodCommand showCategoryAmountByCertainPeriodCommand) {
            return new ShowCategoryAmountByCertainPeriodHandler().handleCommand(showCategoryAmountByCertainPeriodCommand);
        }
        else if (command instanceof AddTransactionCommand addTransactionCommand) {
            return new AddTransactionHandler().handleCommand(addTransactionCommand);
        }
        else if (command instanceof ShowAllExpensesByPeriodOfTimeCommand showAllExpensesByPeriodOfTimeCommand) {
            return new ShowAllExpensesByPeriodOfTimeHandler().handleCommand(showAllExpensesByPeriodOfTimeCommand);
        }
        else {
            return new HandlingResult.Failure("Unknown command");
        }
    }
}
