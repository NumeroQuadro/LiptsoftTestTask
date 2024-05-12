package source.Parsing.ShowAllExpensesByPeriodOfTimeParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

import java.util.Collection;
import java.util.List;

@Component
public class ShowAllExpensesByPeriodOftimeChecker implements Checkable<ShowAllExpensesByPeriodOfTimeCommand> {
    @Override
    public ParsingResult checkCommand(ShowAllExpensesByPeriodOfTimeCommand command) {
        Collection<String> validPeriods = List.of("months");

        if (!validPeriods.contains(command.getPeriod())) {
            return new ParsingResult.Failure("Invalid period. You can choose only from" + validPeriods);
        }

        return new ParsingResult.Success<>(command);
    }
}
