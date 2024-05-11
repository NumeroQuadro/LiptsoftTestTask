package source.Parsing.RemoveCategoryParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.CategoryService;

@Component
public class RemoveCategoryHandler implements Handleable<RemoveCategoryCommand> {
    @Autowired
    private RemoveCategoryChecker checker;
    @Autowired
    private CategoryService categoryService;

    @Override
    public HandlingResult handleCommand(RemoveCategoryCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            categoryService.removeCategory(command.getCategoryName());

            return new HandlingResult.Success("Category removed successfully");
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
