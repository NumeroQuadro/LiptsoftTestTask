package source.Handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import source.Parsing.HandlingResults.HandlingResult;
import source.Parsing.ParsingResults.ParsingResult;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodChecker;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodCommand;
import source.Parsing.ShowCategoryAmountByPeriodParsers.ShowCategoryAmountByCertainPeriodHandler;
import source.Services.TransactionService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShowCategoryAmountByCertainPeriodHandlerTest {
    @Mock
    private ShowCategoryAmountByCertainPeriodChecker checker;
    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private ShowCategoryAmountByCertainPeriodHandler handler;

    @Test
    void handleCommand_WhenCheckerFails_ShouldReturnFailure() {
        var command = new ShowCategoryAmountByCertainPeriodCommand();
        command.setCategoryName("Electronics");
        command.setPeriod("January");

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Failure("Invalid period"));

        HandlingResult result = handler.handleCommand(command);

        assertInstanceOf(HandlingResult.Failure.class, result);
        assertEquals("Unable to complete request, due to: Invalid period", ((HandlingResult.Failure) result).getFailureMessage());
    }

    @Test
    void handleCommand_WhenCommandIsValid_ShouldReturnSuccess() {
        var command = new ShowCategoryAmountByCertainPeriodCommand();
        command.setCategoryName("Electronics");
        command.setPeriod("January");

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Success<>(command));
        when(transactionService.getSumOfTransactionsWithProvidedCategoryNameAndMonth("Electronics", 1)).thenReturn("Sum of transactions is $1000");

        HandlingResult result = handler.handleCommand(command);

        assertInstanceOf(HandlingResult.Success.class, result);
        assertEquals("Sum of transactions is $1000", ((HandlingResult.Success) result).getSuccessMessage());
    }

    @Test
    void handleCommand_WhenServiceThrowsException_ShouldReturnFailure() {
        var command = new ShowCategoryAmountByCertainPeriodCommand();
        command.setCategoryName("Electronics");
        command.setPeriod("January");

        when(checker.checkCommand(command)).thenReturn(new ParsingResult.Success<>(command));
        when(transactionService.getSumOfTransactionsWithProvidedCategoryNameAndMonth("Electronics", 1))
                .thenThrow(new RuntimeException("Database error"));

        HandlingResult result = handler.handleCommand(command);

        assertInstanceOf(HandlingResult.Failure.class, result);
        assertEquals("Unable to complete request, due to: Database error", ((HandlingResult.Failure) result).getFailureMessage());
    }
}
