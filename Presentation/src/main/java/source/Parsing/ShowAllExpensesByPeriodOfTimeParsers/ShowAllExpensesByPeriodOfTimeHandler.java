package source.Parsing.ShowAllExpensesByPeriodOfTimeParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.TransactionService;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class ShowAllExpensesByPeriodOfTimeHandler implements Handleable<ShowAllExpensesByPeriodOfTimeCommand> {
    @Autowired
    private ShowAllExpensesByPeriodOftimeChecker checker;
    @Autowired
    private TransactionService transactionService;

    @Override
    public HandlingResult handleCommand(ShowAllExpensesByPeriodOfTimeCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            var mapResult = transactionService.getTransactionsSumByCategoryInAllMonths();

            Collection<String> result = new ArrayList<>();
            for (var entry : mapResult.entrySet()) {
                result.add(entry.getKey() + " : " + entry.getValue());
            }
            String output = String.join("\n", result);

            return new HandlingResult.Success(output);
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
