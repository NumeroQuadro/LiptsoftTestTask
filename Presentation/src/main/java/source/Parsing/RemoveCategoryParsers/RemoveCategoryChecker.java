package source.Parsing.RemoveCategoryParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

@Component

public class RemoveCategoryChecker implements Checkable<RemoveCategoryCommand> {
    @Override
    public ParsingResult checkCommand(RemoveCategoryCommand command) {
        if (command.getCategoryName().isEmpty()) {
            return new ParsingResult.Failure("Category name must not be empty");
        }

        return new ParsingResult.Success<>(command);
    }
}
