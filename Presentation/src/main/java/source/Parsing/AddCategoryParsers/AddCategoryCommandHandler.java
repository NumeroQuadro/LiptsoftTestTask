package source.Parsing.AddCategoryParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.CategoryService;
import source.Services.MccPerCategoryService;

@Component
public class AddCategoryCommandHandler implements Handleable<AddCategoryCommand> {
    @Autowired
    private AddCategoryCommandChecker checker;
    @Autowired
    private MccPerCategoryService mccPerCategoryService;

    @Override
    public HandlingResult handleCommand(AddCategoryCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, dut to: " + failure.getFailureMessage());
        }

        try {
            mccPerCategoryService.addNewCategoryWithMcc(command.getCategoryName(), command.getMccs());

            return new HandlingResult.Success("Category added successfully");
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }
    }
}
