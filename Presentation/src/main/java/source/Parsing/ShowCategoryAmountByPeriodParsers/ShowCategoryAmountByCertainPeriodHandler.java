package source.Parsing.ShowCategoryAmountByPeriodParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.TransactionService;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;

@Component
public class ShowCategoryAmountByCertainPeriodHandler implements Handleable<ShowCategoryAmountByCertainPeriodCommand> {
    @Autowired
    private ShowCategoryAmountByCertainPeriodChecker checker;
    @Autowired
    private TransactionService transactionService;
    @Override
    public HandlingResult handleCommand(ShowCategoryAmountByCertainPeriodCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            var month = convertStringToMonthNumber(command.getPeriod());
            var result = transactionService.getSumOfTransactionsWithProvidedCategoryNameAndMonth(command.getCategoryName(), month);

            return new HandlingResult.Success(result);
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }

    private int convertStringToMonthNumber(String monthName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);

        Month month = Month.from(formatter.parse(monthName));

        return (int) month.getLong(ChronoField.MONTH_OF_YEAR);
    }
}
