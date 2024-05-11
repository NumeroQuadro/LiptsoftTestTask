package source.Parsing.AddGroupToCategoryParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

@Component
public class AddGroupToExistingCategoryChecker implements Checkable<AddGroupToExistingCategoryCommand> {
    @Override
    public ParsingResult checkCommand(AddGroupToExistingCategoryCommand command) {
        if (command.getCategoryName().isEmpty()) {
            return new ParsingResult.Failure("Category name must not be empty");
        }

        if (command.getCategories().isEmpty()) {
            return new ParsingResult.Failure("Categories to add must not be empty");
        }

        return new ParsingResult.Success<>(command);
    }
}
