package source.Parsing.ShowCategoriesParsers;

import org.springframework.stereotype.Component;
import source.Parsing.Checkable;
import source.Parsing.ParsingResults.ParsingResult;

@Component
public class ShowCategoriesListChecker implements Checkable<ShowCategoriesListCommand> {
    @Override
    public ParsingResult checkCommand(ShowCategoriesListCommand command) {
        return new ParsingResult.Success<>(command);
    }
}
