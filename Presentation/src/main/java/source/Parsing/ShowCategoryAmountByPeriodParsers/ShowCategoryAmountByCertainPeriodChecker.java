package source.Parsing.ShowCategoryAmountByPeriodParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ShowCategoryAmountByCertainPeriodChecker implements Checkable<ShowCategoryAmountByCertainPeriodCommand> {
    @Autowired
    private PermittedPeriodsConfiguration permittedPeriodsConfiguration;
    @Override
    public ParsingResult checkCommand(ShowCategoryAmountByCertainPeriodCommand command) {
        Collection<String> allowedPeriods = permittedPeriodsConfiguration.getPeriods();

        if (command.getCategoryName() == null) {
            return new ParsingResult.Failure("Category name is not specified");
        }
        if (command.getPeriod() == null) {
            return new ParsingResult.Failure("Period is not specified");
        }
        if (!allowedPeriods.contains(command.getPeriod())) {
            return new ParsingResult.Failure("Period is not allowed. You can specify only: " + allowedPeriods);
        }

        return new ParsingResult.Success<>(command);
    }
}
