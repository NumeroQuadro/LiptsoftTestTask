package source.Parsing.AddMccToCategoryParsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import source.Parsing.Handleable;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.MccPerCategoryService;

@Component
public class AddMccToExistingCategoryHandler implements Handleable<AddMccToExistingCategoryCommand> {
    @Autowired
    private AddMccToExistingCategoryChecker checker;
    @Autowired
    private MccPerCategoryService mccPerCategoryService;

    @Override
    public HandlingResult handleCommand(AddMccToExistingCategoryCommand command) {
        var checkerResult = checker.checkCommand(command);

        if (checkerResult instanceof ParsingResult.Failure failure) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + failure.getFailureMessage());
        }

        try {
            for (var mcc : command.getMccs()) {
                mccPerCategoryService.addNewMccToCategory(command.getCategoryName(), mcc);
            }

            return new HandlingResult.Success("MCCs added successfully");
        }
        catch (Exception e) {
            return new HandlingResult.Failure("Unable to complete request, due to: " + e.getMessage());
        }

    }
}
