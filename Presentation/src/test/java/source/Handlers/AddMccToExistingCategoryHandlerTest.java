package source.Handlers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryChecker;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryCommand;
import source.Parsing.AddMccToCategoryParsers.AddMccToExistingCategoryHandler;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Services.MccPerCategoryService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddMccToExistingCategoryHandlerTest {

    @Mock
    private AddMccToExistingCategoryChecker checker;

    @Mock
    private MccPerCategoryService mccPerCategoryService;

    @InjectMocks
    private AddMccToExistingCategoryHandler handler;

    @Test
    void handleCommand_CheckerReturnsFailure_ReturnsHandlingFailure() {
        AddMccToExistingCategoryCommand command = new AddMccToExistingCategoryCommand();
        command.setCategoryName("Electronics");
        command.setMccs(Arrays.asList("1234", "5678"));

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Failure("Invalid input"));

        HandlingResult result = handler.handleCommand(command);

        assertInstanceOf(HandlingResult.Failure.class, result);
        assertEquals("Unable to complete request, due to: Invalid input", ((HandlingResult.Failure) result).getFailureMessage());
    }

    @Test
    void handleCommand_CheckerReturnsSuccess_AddsMccsSuccessfully() {
        AddMccToExistingCategoryCommand command = new AddMccToExistingCategoryCommand();
        command.setCategoryName("Electronics");
        command.setMccs(Arrays.asList("1234", "5678"));

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Success<>(command));

        HandlingResult result = handler.handleCommand(command);

        verify(mccPerCategoryService, times(2)).addNewMccToCategory(eq("Electronics"), anyString());
        assertInstanceOf(HandlingResult.Success.class, result);
        assertEquals("MCCs added successfully", ((HandlingResult.Success) result).getSuccessMessage());
    }

    @Test
    void handleCommand_ServiceThrowsException_ReturnsHandlingFailure() {
        AddMccToExistingCategoryCommand command = new AddMccToExistingCategoryCommand();
        command.setCategoryName("Electronics");
        command.setMccs(List.of("1234"));

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Success<>(command));
        doThrow(new RuntimeException("Database error")).when(mccPerCategoryService).addNewMccToCategory(anyString(), anyString());

        HandlingResult result = handler.handleCommand(command);

        assertInstanceOf(HandlingResult.Failure.class, result);
        assertEquals("Unable to complete request, due to: Database error", ((HandlingResult.Failure) result).getFailureMessage());
    }
}
