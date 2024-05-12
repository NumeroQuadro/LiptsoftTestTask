package source.Parsing.AddTransactionCommandParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

import java.math.BigDecimal;

@Component
public class AddTransactionChecker implements Checkable<AddTransactionCommand> {
    @Override
    public ParsingResult checkCommand(AddTransactionCommand command) {
        if (command.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new ParsingResult.Failure("Amount of transaction should be positive");
        }

        if (!command.getMcc().isEmpty() && command.getMcc().length() != 4) {
            return new ParsingResult.Failure("MCC should be 4 digits long");
        }

        return new ParsingResult.Success<>(command);
    }
}
