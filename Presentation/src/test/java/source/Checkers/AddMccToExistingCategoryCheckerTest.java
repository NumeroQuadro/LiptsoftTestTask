package source.Checkers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import source.Parsing.ParsingResults.ParsingResult;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryChecker;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryCommand;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddMccToExistingCategoryCheckerTest {

    @InjectMocks
    private AddMccToExistingCategoryChecker checker;

    private AddMccToExistingCategoryCommand command;

    @BeforeEach
    void setUp() {
        command = new AddMccToExistingCategoryCommand();
    }

    @Test
    void shouldReturnFailureWhenCategoryNameIsEmpty() {
        command.setCategoryName("");
        command.setMccs(Arrays.asList("1234", "5678"));

        ParsingResult result = checker.checkCommand(command);

        assertInstanceOf(ParsingResult.Failure.class, result);
        assertEquals("Category name must not be empty", ((ParsingResult.Failure) result).getFailureMessage());
    }

    @Test
    void shouldReturnFailureWhenMccListIsEmpty() {
        command.setCategoryName("Electronics");
        command.setMccs(Collections.emptyList());

        ParsingResult result = checker.checkCommand(command);

        assertInstanceOf(ParsingResult.Failure.class, result);
        assertEquals("MCCs to add must not be empty", ((ParsingResult.Failure) result).getFailureMessage());
    }

    @Test
    void shouldReturnSuccessWhenCommandIsValid() {
        command.setCategoryName("Electronics");
        command.setMccs(Arrays.asList("1234", "5678"));

        ParsingResult result = checker.checkCommand(command);

        assertInstanceOf(ParsingResult.Success.class, result);
    }

}
