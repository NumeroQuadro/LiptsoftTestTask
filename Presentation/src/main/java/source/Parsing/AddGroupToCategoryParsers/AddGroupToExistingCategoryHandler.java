package source.Parsing.AddGroupToCategoryParsers;

import org.springframework.beans.factory.annotation.Autowired;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.CategoryPerCategoryService;

public class AddGroupToExistingCategoryHandler implements Handleable<AddGroupToExistingCategoryCommand> {
    @Autowired
    private AddGroupToExistingCategoryChecker checker;
    @Autowired
    private CategoryPerCategoryService categoryPerCategoryService;

    @Override
    public HandlingResult handleCommand(AddGroupToExistingCategoryCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            for (var category : command.getCategories()) {
                categoryPerCategoryService.addGroupToCategory(command.getCategoryName(), category);
            }

            return new HandlingResult.Success("Categories added successfully");
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
