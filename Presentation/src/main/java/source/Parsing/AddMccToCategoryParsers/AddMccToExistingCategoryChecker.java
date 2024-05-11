package source.Parsing.AddMccToCategoryParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

@Component

public class AddMccToExistingCategoryChecker implements Checkable<AddMccToExistingCategoryCommand> {

    @Override
    public ParsingResult checkCommand(AddMccToExistingCategoryCommand command) {
        if (command.getCategoryName().isEmpty()) {
            return new ParsingResult.Failure("Category name must not be empty");
        }

        if (command.getMccs().isEmpty()) {
            return new ParsingResult.Failure("MCCs to add must not be empty");
        }

        return new ParsingResult.Success<>(command);
    }
}
