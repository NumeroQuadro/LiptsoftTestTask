package source.Parsing.AddCategoryParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.Commandable;
import source.Parsing.ParsingResults.ParsingResult;

@Component
public class AddCategoryCommandChecker implements Checkable<AddCategoryCommand> {
    @Override
    public ParsingResult checkCommand(AddCategoryCommand command) {
        var mccs = command.getMccs();

        for (var mcc : mccs) {
            if (mcc.length() != 4) {
                return new ParsingResult.Failure("MCC code must be 4 digits long");
            }
        }

        if (command.getCategoryName().isEmpty()) {
            return new ParsingResult.Failure("Category name must not be empty");
        }

        return new ParsingResult.Success<>(command);
    }
}
