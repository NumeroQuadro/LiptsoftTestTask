package source.CommandLineInterpreters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import source.Parsing.RemoveCategoryParsers.RemoveCategoryCommand;
import source.Parsing.RemoveCategoryParsers.RemoveCategoryHandler;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeCommand;
import source.Parsing.ShowAllExpensesByPeriodOfTimeParsers.ShowAllExpensesByPeriodOfTimeHandler;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListCommand;
import source.Parsing.ShowCategoriesParsers.ShowCategoriesListHandler;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodCommand;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodHandler;

@Component
public class ConsoleCommandLineInterpreter implements Interpretable {
    @Autowired
    private AddCategoryCommandHandler addCategoryCommandHandler;
    @Autowired
    private ConsoleMessagesHandler consoleMessagesHandler;
    @Autowired
    private AddGroupToExistingCategoryHandler addGroupToExistingCategoryHandler;
    @Autowired
    private AddMccToExistingCategoryHandler addMccToExistingCategoryHandler;
    @Autowired
    private RemoveCategoryHandler removeCategoryHandler;
    @Autowired
    private ShowCategoriesListHandler showCategoriesListHandler;
    @Autowired
    private ShowCategoryAmountByCertainPeriodHandler showCategoryAmountByCertainPeriodHandler;
    @Autowired
    private AddTransactionHandler addTransactionHandler;
    @Autowired
    private ShowAllExpensesByPeriodOfTimeHandler showAllExpensesByPeriodOfTimeHandler;


    @Override
    public void processArgumentLines(String[] args) {
        CommandLine commandLine = new CommandLine(new MainCommand());
        commandLine.setUsageHelpWidth(100);
        commandLine.setColorScheme(new CommandLine.Help.ColorScheme.Builder()
                .commands(CommandLine.Help.Ansi.Style.fg_yellow)
                .options(CommandLine.Help.Ansi.Style.fg_green)
                .parameters(CommandLine.Help.Ansi.Style.fg_cyan)
                .optionParams(CommandLine.Help.Ansi.Style.italic)
                .build());

        try {
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
        catch (CommandLine.ParameterException e) {
            consoleMessagesHandler.handleFailureMessage(e.getMessage());
        }
    }

    private HandlingResult handleCommand(Object command) {
        if (command instanceof AddCategoryCommand addCategoryCommand) {
            return addCategoryCommandHandler.handleCommand(addCategoryCommand);
        }
        else if (command instanceof AddGroupToExistingCategoryCommand addGroupToExistingCategoryCommand) {
            return addGroupToExistingCategoryHandler.handleCommand(addGroupToExistingCategoryCommand);
        }
        else if (command instanceof AddMccToExistingCategoryCommand addMccToExistingCategoryCommand) {
            return addMccToExistingCategoryHandler.handleCommand(addMccToExistingCategoryCommand);
        }
        else if (command instanceof RemoveCategoryCommand removeCategoryCommand) {
            return removeCategoryHandler.handleCommand(removeCategoryCommand);
        }
        else if (command instanceof ShowCategoriesListCommand showCategoriesListCommand) {
            return showCategoriesListHandler.handleCommand(showCategoriesListCommand);
        }
        else if (command instanceof ShowCategoryAmountByCertainPeriodCommand showCategoryAmountByCertainPeriodCommand) {
            return showCategoryAmountByCertainPeriodHandler.handleCommand(showCategoryAmountByCertainPeriodCommand);
        }
        else if (command instanceof AddTransactionCommand addTransactionCommand) {
            return addTransactionHandler.handleCommand(addTransactionCommand);
        }
        else if (command instanceof ShowAllExpensesByPeriodOfTimeCommand showAllExpensesByPeriodOfTimeCommand) {
            return showAllExpensesByPeriodOfTimeHandler.handleCommand(showAllExpensesByPeriodOfTimeCommand);
        }
        else {
            return new HandlingResult.Failure("Unknown command");
        }
    }
}
