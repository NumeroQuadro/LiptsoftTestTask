package source.Parsing.AddTransactionCommandParsers;

import org.springframework.beans.factory.annotation.Autowired;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.TransactionService;

public class AddTransactionHandler implements Handleable<AddTransactionCommand> {
    @Autowired
    private AddTransactionChecker checker;
    @Autowired
    private TransactionService transactionService;

    @Override
    public HandlingResult handleCommand(AddTransactionCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            if (command.getMcc() == null) {
                transactionService.addNewTransactionWithoutMcc(command.getAmount(), command.getDate());
            } else {
                transactionService.addNewTransaction(command.getAmount(), command.getDate(), command.getMcc());
            }

            return new HandlingResult.Success("Transaction added successfully");
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
