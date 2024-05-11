package source.Parsing.ShowCategoriesParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.CategoryService;

@Component
public class ShowCategoriesListHandler implements Handleable<ShowCategoriesListCommand> {
    @Autowired
    private ShowCategoriesListChecker checker;
    @Autowired
    private CategoryService categoryService;

    @Override
    public HandlingResult handleCommand(ShowCategoriesListCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            var categories = categoryService.getAllCategories();

            return new HandlingResult.Success("Categories listed successfully: " + categories.toString().replace("[", "").replace("]", ""));
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
